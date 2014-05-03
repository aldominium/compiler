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

      

%token BEGIN          /* begin  */
%token END          /* end  */
%token PRINT          /* print  */
%token READ          /* read  */
%token EQUALS        /*:=*/
%token OR        /*:=*/
%token WHILE        /*:=*/
%token DO        /*:=*/
%token FUNCTION        /*:=*/
%token IF        /*:=*/
%token THEN        /*:=*/
%token PROGRAM        /*:=*/
%token VAR        /*:=*/
%token NL          /* newline  */
%token INT        /*:=*/
%token FLOAT        /*:=*/



%token <dval> NUMF  /* a number */
%token <ival> NUMENT  /* a number */
%token <sval> ID


%type <obj> decls
%type <obj> dec
%type <sval> tipo

/*%type <obj> fun_decls*/
/*%type <obj> fun_dec*/
/*%type <obj> params*/
/*%type <obj> param*/
/*%type <obj> stmt*/
/*%type <obj> stmt_lst*/
%type <obj> var
/*%type <obj> expr_lst*/
%type <obj> expresion
%type <obj> expr
%type <obj> term
%type <obj> factor
/*%type <obj> relop*/
/*%type <obj> sumop*/
/*%type <obj> prodop*/
/*%type <obj> signo*/
%start prog



%left '-' '+'
%left '*' '/'
%right EQUALS
%left NEG          /* negation--unary minus */
%right '^'         /* exponentiation        */    
%%

prog : PROGRAM ID ';' decls BEGIN stmt_lst END '.' {
                                          System.out.println("programa");
                                          } 
;

decls : decls ';' dec {System.out.println("decls");} 
      | dec             {System.out.println("decls");}
      |               {System.out.println("decls");}
;

dec : VAR ID ':' tipo { 
                        System.out.println("dec");
                          }//Add ID a tabla de simb
;
 
tipo : INT {
    
            }//Se crea nodo int para tabla de simbolos
     | FLOAT {
              
              }//Se crea nodo float para tabla de simbolos
;

stmt : var EQUALS expr {
    
                      }
     | IF expresion THEN stmt {

                              }
     | WHILE expresion DO stmt {
                                }
     | BEGIN stmt_lst END {

                          }//Se pasa nodo
     | READ var {

                }
     | PRINT expr {

                  }
;


stmt_lst : stmt ';' stmt_lst {
                            System.out.println("vacio");
                              }
         | stmt  {
                  System.out.println("vacio");
                }
       //  | {
            //System.out.println("vacio");
           //}//Nodo null
;

var : ID {
  
          }//new NodoArbol($1.sval);}
;

expresion : expr {
  
                  }//Se pasa nodo
          | expr '<' expr {

                              }
          | expr '=' expr {

                          }
          | expr '>' expr {

                              }
;

expr : expr '+' term {
  
                      }
     | expr '-' term {

                        }
     | expr '|' term {

                    }
     | '-' term {

                  }
     | term {

            }//Se pasa nodo             
;

term : term '*' factor {
  
                        }                 
     | term '/' factor {

                          }
     | term '&' factor {

                      }
     | factor {

              }             
;

factor : '(' expresion ')' {
  
                            }//Se pasa nodo
       | ID    {

                }
       | NUMENT  {

                }
       | NUMF  {

                }
;

%%
  static Hashtable<String, Double> simbolos
     = new Hashtable<String, Double>();

  static Nodo nodoInicial;
  static Nodo nodoDeclaraciones;


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

    //nodoInicial.imprimir();

    //Nodo inicio = nodoInicial.getRightChild();
    

    //Interprete interprete = new Interprete();
    //interprete.parseProgram(inicio,simbolos);
    
  }