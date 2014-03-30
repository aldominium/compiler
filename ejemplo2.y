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
                           nodoInicial = new Nodo(null,";",(Nodo)$2);
                          }
;

stmt: ID EQUALS expr         {
                              simbolos.put($1,0.0);
                              $$ = new Nodo(new Nodo("ID",$1),":=",(Nodo)$3);
                             }
    |IF expresion THEN stmt  {
                              $$ = new Nodo((Nodo)$2,"IF",(Nodo)$4);
                             }
    |BEGIN stmt_lst END      {
                              $$ = (Nodo)$2;
                             }
    | PRINT expr             {
                              $$ = new Nodo("PRINT",(Nodo)$2);
                             }
    | READ ID                {
                              simbolos.put($2,0.0);
                              $$ = new Nodo("READ",new Nodo("ID",$2));
                             }
;


stmt_lst: stmt ';' stmt_lst  {
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
    | term                   {
                              $$ = $1;
                              }
;


term: term '*' factor        {$$ = new Nodo((Nodo)$1,"*",(Nodo)$3);}
    | term '/' factor        {$$ = new Nodo((Nodo)$1,"/",(Nodo)$3);}
    | factor                 {
                              $$ = $1;
                             }
;


factor: '(' expr ')'         {$$ = (Nodo)$2;}
      | ID                   {
                              simbolos.put($1,0.0);
                              $$ = new Nodo("ID",$1);
                             }
      | NUM                  {
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
    

    Interprete interprete = new Interprete();
    interprete.parseProgram(inicio,simbolos);
    
  }
