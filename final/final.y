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
%token <sval> INT        /*:=*/
%token <sval> FLOAT        /*:=*/
%token OR        /*:=*/
%token WHILE        /*:=*/
%token DO        /*:=*/
%token FUNCTION        /*:=*/
%token IF        /*:=*/
%token THEN        /*:=*/
%token PROGRAM        /*:=*/
%token VAR        /*:=*/


%token <dval> NUMF  /* a number */
%token <dval> NUMENT  /* a number */
%token <sval> ID


%type <sval> tipo
%type <sval> relop
%type <sval> sumop
%type <sval> prodop
%type <sval> signo

%type <obj> stmt_lst
%type <obj> stmt
%type <sval> var
%type <obj> expr
%type <obj> term
%type <obj> stmt
%type <obj> factor
%type <obj> expresion
%type <obj> expr_lst
%type <obj> params
%type <obj> param


   
%%

prog : PROGRAM ID ';' decls fun_decls BEGIN stmt_lst END '.' {
                                          tablaGlobal.imprimir();
                                          System.out.println("program");
                                          nodoPrincipal = new Nodo(null,";",(Nodo)$7);
                                          } 
;

decls : decls ';' dec { } //No las necesito, me voy directo a dec
      | dec             {}
      |               {}
;

dec : VAR ID ':' tipo { 
                        tablaGlobal.añadirDeclaracionVariable($2,$4);
                          }//Add ID a tabla de simb
;
 
tipo : INT {
              //System.out.println("tipo:" + $1);
              $$ = "int";


            }//Se crea nodo int para tabla de simbolos
     | FLOAT {
              //System.out.println("tipo:");
              $$ = "float";
              }//Se crea nodo float para tabla de simbolos
;

fun_decls : fun_dec ';' fun_decls { 

                                  } 
          | fun_dec             {}
          |               {}
;

fun_dec : FUNCTION ID '('params')' ':' tipo BEGIN stmt_lst END{
                                  TablaSimbolos tablaLocal = new TablaSimbolos();
                                  tablaLocal.añadirDeclaracionesVariables((Nodo)$4);
                                  tablaLocal.añadirVariableFuncion($2,$7);
                                  tablaGlobal.añadirDeclaracionFuncion($2,$7,numParams,tablaLocal,new Nodo(null,";",(Nodo)$9));
                                  numParams = 0;
                                  //tablaLocal.imprimir();
                                  System.out.println();
                                  }
;

params : param ';' params {
                    $$ = new Nodo((Nodo)$1,";",(Nodo)$3);
                  }
      | param{
              $$ = new Nodo((Nodo)$1,";",null);
            }
      | {
      
            }     
;

param : ID ':' tipo {
                    $$ = new Nodo(new Nodo("ID",$1),":",new Nodo("tipo",$3));
                    numParams ++;
                  }    
;



stmt : var EQUALS expr {
                          $$ = new Nodo(new Nodo("ID",$1),":=",(Nodo)$3);

                      }
     | IF expr THEN stmt {
                          $$ = new Nodo((Nodo)$2,"IF",(Nodo)$4);
                              }
     | WHILE expr DO stmt {
                          $$ = new Nodo((Nodo)$2,"WHILE",(Nodo)$4);
                                }
     | BEGIN stmt_lst END {
                          $$ = $2;
                          }
     | READ var {
                  $$ = new Nodo("READ",new Nodo("ID",$2));
                }
     | PRINT expr {
                  $$ = new Nodo("PRINT",(Nodo)$2);
                  }
;


stmt_lst : stmt ';' stmt_lst {
                                $$ = new Nodo((Nodo)$1,";",(Nodo)$3);
                              }
         | stmt  {
                  $$ = new Nodo((Nodo)$1,";",null);//$$ =new Nodo((Nodo)$1,";",null);
                }
         | {
            //System.out.println("vacio");
           }//Nodo null
;

var : ID {
          $$ = $1;
          }
;

expr_lst : expr ',' expr_lst {
                              $$ = new Nodo((Nodo)$1,",",(Nodo)$3);
                            }
         |expr{
                $$ = new Nodo((Nodo)$1,",",null);
              }
          |{

          }
;

expresion : expr {
                  $$ = (Nodo)$1;
                  }
          |expr relop  expr{
                            $$ = new Nodo((Nodo)$1,$2,(Nodo)$3);
                          }

;

expr : expr sumop term {
                        $$ = new Nodo((Nodo)$1,$2,(Nodo)$3);
                      }
      |signo term{
                    $$ = new Nodo("signo",(Nodo)$2);
                  }
      |term{
          $$ = $1;
          }
                 
;

term : term prodop factor {
                        $$ = new Nodo((Nodo)$1,$2,(Nodo)$3);
  
                        }
      |factor{
          $$ = $1;
      }                          
;

factor : '(' expresion ')' {
                          $$ = $2;
  
                            }
        |ID{
                        $$ = new Nodo("ID",$1);//talvez ponga variable
          }
        |NUMENT{
                      $$ = new Nodo("NUMENT",$1);
              }
        |NUMF{
                  $$ = new Nodo("NUMF",$1);
            }
        |ID '(' expr_lst ')'{
                            $$ = new Nodo(new Nodo("ID",$1),"call",(Nodo)$3);
                            }
        |'!' factor{
                          $$ = new Nodo("!",(Nodo)$2);
                    }
;
relop : '<' {
                  $$ = "<";
                  }
      | '='{
                $$ = "=";
            }
      | '>'{
              $$ = ">";
            }     
;

sumop : '+' {
              $$ = "+";
                  }
      | '-'{
            $$ = "-";
            }
      | OR {
            $$ = "||";
            }     
;

prodop : '*' {
              $$ = "*";
              }
      | '/'{
            $$ = "/";
            }
      | '&' {
            $$ = "&";
            }     
;

signo : '+' {
            $$ = "+";
            }
      | '-'{
            $$ = "-";
            }    
;



%%
  static TablaSimbolos tablaGlobal = new TablaSimbolos();
  int numParams = 0;
  static Nodo nodoPrincipal;

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
    System.out.println(error);
    
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
      //System.out.print("Expression: ");
      interactive = true;
      yyparser = new Parser( new FileReader(args[0]));


    yyparser.yyparse();

    Nodo nodoInicial = nodoPrincipal.getRightChild();

    /*while(nodoInicial.getRightChild() != null){
      System.out.println();
      nodoInicial.getLeftChild().imprimir();
      nodoInicial = nodoInicial.getRightChild();
    }*/
    

    Interprete interprete = new Interprete(tablaGlobal);
    interprete.parseProgram(nodoPrincipal.getRightChild());

    
  }
