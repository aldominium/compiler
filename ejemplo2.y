/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2001 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * This is a modified version of the example from                          *
 *   http://www.lincom-asg.com/~rjamison/byacc/                            *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * This program is free software; you can redistribute it and/or modify    *
 * it under the terms of the GNU General Public License. See the file      *
 * COPYRIGHT for more information.                                         *
 *                                                                         *
 * This program is distributed in the hope that it will be useful,         *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the           *
 * GNU General Public License for more details.                            *
 *                                                                         *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA                 *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%{
  import java.io.*;
  import java.util.*;

  
%}

      
%token NL          /* newline  */
%token BEGIN          /* begin  */
%token END          /* end  */
%token PRINT          /* print  */
%token READ          /* read  */
%token EQUALS        /*:=*/
%token IF        /*:=*/
%token THEN        /*:=*/



%token <dval> NUM  /* a number */
%token <sval> ID

%type <obj> expr
%type <obj> expresion
%type <obj> stmt
%type <obj> stmt_lst

%type <obj> term
%type <obj> factor


%left '-' '+'
%left '*' '/'
%right EQUALS
%left NEG          /* negation--unary minus */
%right '^'         /* exponentiation        */    
%%




prg:  BEGIN stmt_lst END  { 
                            System.out.println("prg");
                           nodoInicial = new Nodo(null,";",(Nodo)$2);
                          }
;

stmt: ID EQUALS expr         {
                              simbolos.put($1,0.0);
                              System.out.println("stmt");
                              $$ = new Nodo(new Nodo("ID",$1),":=",(Nodo)$3);
                             }
    |IF expresion THEN stmt  {
                              $$ = new Nodo((Nodo)$2,"IF",(Nodo)$4);
                             }
    |BEGIN stmt_lst END      {
                              $$ = (Nodo)$2;
                             }
    | PRINT expr             {
                              System.out.println("stmt");
                              $$ = new Nodo("PRINT",(Nodo)$2);
                             }
    | READ ID                {
                              simbolos.put($2,0.0);
                              $$ = new Nodo("READ",new Nodo("ID",$2));
                             }
;


stmt_lst: stmt ';' stmt_lst  {
                              System.out.println("stmt_lst");
                              $$ = new Nodo((Nodo)$1,";",(Nodo)$3);
                             }
          |                  {$$ = null;}
;


expresion: expr '<' expr     {$$ = new Nodo((Nodo)$1,"<",(Nodo)$3);}
          | expr '>' expr    {$$ = new Nodo((Nodo)$1,">",(Nodo)$3);}
          | expr '=' expr    {$$ = new Nodo((Nodo)$1,"=",(Nodo)$3);}
;


expr: expr '+' term          {$$ = new Nodo((Nodo)$1,"+",(Nodo)$3);}
    | expr '-' term         {$$ = new Nodo((Nodo)$1,"-",(Nodo)$3);}
    | term                   {System.out.println("entre expr");
                              $$ = $1;
                              }
;


term: term '*' factor        {$$ = new Nodo((Nodo)$1,"*",(Nodo)$3);}
    | term '/' factor        {$$ = new Nodo((Nodo)$1,"/",(Nodo)$3);}
    | factor                 {System.out.println("entre term");
                              $$ = $1;
                             }
;


factor: '(' expr ')'         {$$ = (Nodo)$2;}
      | ID                   {
                              System.out.println("factor id" + $1);
                              simbolos.put($1,0.0);
                              $$ = new Nodo("ID",$1);
                             }
      | NUM                  {
                              System.out.println("factor num");
                              $$ = new Nodo("NUM",$1);
                              }

%%
  static Hashtable<String, Double> simbolos
     = new Hashtable<String, Double>();

  static Nodo nodoInicial;


  private Yylex lexer;


  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }


  public void yyerror (String error) {
    
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }


  static boolean interactive;

  public static void main(String args[]) throws IOException {
    System.out.println("BYACC/Java with JFlex Calculator Demo");

    Parser yyparser;
    
      // interactive mode
      System.out.println("[Quit with CTRL-D]");
      System.out.print("Expression: ");
      interactive = true;
      yyparser = new Parser(new InputStreamReader(System.in));


    yyparser.yyparse();

    Nodo inicio = nodoInicial.getRightChild();
    
    inicio.imprimir();
    
    /*
    System.out.println("Tipo hijo der izq der der : "+inicio.getRightChild().getLeftChild().getRightChild().getRightChild().getKind());
    
    System.out.println("Tipo hijo der izq der der : "+inicio.getRightChild().getLeftChild().getRightChild().getRightChild().getLeftChild().getLeftChild().getKind());

    System.out.println("Tipo hijo der izq der der : "+inicio.getRightChild().getLeftChild().getRightChild().getRightChild().getLeftChild().getLeftChild().getID());

    System.out.println("Tipo hijo der izq der der : "+inicio.getRightChild().getLeftChild().getRightChild().getRightChild().getLeftChild().getRightChild().getValor());
    
    System.out.println("Tipo hijo der izq der der : "+inicio.getRightChild().getLeftChild().getRightChild().getRightChild().getLeftChild().getRightChild().getRightChild().getKind());

    System.out.println("Tipo hijo der izq der der : "+inicio.getRightChild().getLeftChild().getRightChild().getRightChild().getLeftChild().getRightChild().getRightChild().getValor());
    
    System.out.println(simbolos.size());
    */
    System.out.println();
    System.out.println();
    System.out.println();

    /*System.out.println("Valor hijo der izq izq  : "+inicio.getRightChild().getRightChild().getLeftChild().getLeftChild().getID());*/

    Interprete interprete = new Interprete();
    interprete.parseProgram(inicio,simbolos);
    
  }
