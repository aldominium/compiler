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



%token <dval> NUM  /* a number */
%token <sval> ID

%type <dval> expr

%type <dval> term
%type <dval> factor


%left '-' '+'
%left '*' '/'
%right EQUALS
%left NEG          /* negation--unary minus */
%right '^'         /* exponentiation        */    
%%




prg:  BEGIN stmt_lst END  { 
                          
                          }
;


stmt_lst: stmt ';' stmt_lst  {/*System.out.println("entre stmt_lst");*/}
          |                  {}
;

stmt: ID EQUALS expr         {
                              simbolos.put($1,$3);
                              /*System.out.println($1 + " es igual a: "+ simbolos.get($1));*/
                              
                             }
    | PRINT expr             {
                              System.out.println("Expresion igual a: " + $2);
                             }
    ;
    | READ ID                {/*
                              if(simbolos.containsKey($2))
                                System.out.println("Valor de: " + $2 + " "+simbolos.get($2));
                              else
                                System.out.println("0.0");*/
                              Scanner in = new Scanner(System.in);
                              Double miValor = in.nextDouble();
                              simbolos.put($2,miValor);
                             }
;



expr: expr '+' term          {$$ = $1 + $3;}
    | expr '-' term         {$$ = $1 - $3;}
    | term                   {/*System.out.println("entre expr");
                              $$ = $1;*/}
;

term: term '*' factor        {$$ = $1 * $3;}
    | term '/' factor        {$$ = $1 / $3;}
    | factor                 {/*System.out.println("entre term");
                              $$ = $1;*/}
;

factor: '(' expr ')'         {$$ = $2;}
      | ID                   {
                              if(simbolos.containsKey($1)){
                                /*System.out.println("valor simbolo factor:"+ simbolos.get($1));*/
                                $$ = simbolos.get($1);

                              }
                              else{
                                /*System.out.println("valor simbolo factor:"+ simbolos.get($1));*/
                                simbolos.put($1,0.0);
                                $$ = 0.0;
                              }
                             }
      | NUM                  {$$ = $1;}

%%
  Hashtable<String, Double> simbolos
     = new Hashtable<String, Double>();

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
    
  }
