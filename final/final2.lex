/*Reconocedor Léxico*/

/*
	
	Correr jflex: 				 > jflex Lexer.lex
  
  
*/ 



import java.util.*;
import java.io.*;

%%

%class Lexico
%unicode
%byaccj

%{
	 // Parser es un atributo del reconocedor léxico. Cuando el parser
   // (reconocedor sintáctico) crea al léxico, se pasa a sí mismo para que
   // parser lo apunte

    private Parser parser;

   // Constructor del reconocedor léxico.
	 // Recibe un reader y el parser
   public Lexico(Reader r, Parser par) {
	  	this(r);
	  	parser = par;
   }
   
%}

//Fin de archivo
/*%integer
%eofval{
  return 31;
%eofval}
%eofclose*/


ID = ([a-zA-Z]|_)(_|[0-9]|[a-zA-Z])* 
NUMENTERO = 0|[1-9][0-9]*
NUMFLOAT = ([0-9])+"."([0-9])* //[0-9][0-9]*.[0-9][0-9]*

%%

\r|\n|\r\n {/*Se ignora retorno de carro*/}

/*Palabras reservadas*/

"program" {return Parser.PROGRAM;}
"var"			{return Parser.VAR;}
"int"			{return Parser.INT;}
"float"		{return Parser.FLOAT;}
"if" 	  	{return Parser.IF;}
"then"  	{return Parser.THEN;}
"while"		{return Parser.WHILE;}
"do"		  {return Parser.DO;}
"begin"		{return Parser.BEGIN;}
"end"		  {return Parser.END;}
"read"		{return Parser.READ;}
"print"		{return Parser.PRINT;}
"."       {return Parser.PUNTO;}
":"				{return Parser.DPUNTO;}

/*relop*/
"<"				{return Parser.MENORQ;}
"="				{return Parser.EQ;}
">"				{return Parser.MAYORQ;}

	

/*Separadores*/

";" {return Parser.PCOMA;}
"(" {return Parser.PARI;}
")" {return Parser.PARD;}

/*Operadores*/

"+"  {return Parser.SUMA;}
"-"  {return Parser.RESTA;}
"||"  {return Parser.OR;}
"*"  {return Parser.POR;}
"/"  {return Parser.ENTRE;}
"&"	{return Parser.AND;}
":=" {return Parser.DPEQ;}


/*identificador*/
{ID} {parser.yylval = new NodoArbol(yytext());/*new ParserVal(yytext());*/ return Parser.ID;}

/*Numero*/
{NUMENTERO} {parser.yylval = new NodoArbol(new Integer(yytext()), 1);/*new ParserVal( new Integer(yytext()) )*/ return Parser.NUME;}
{NUMFLOAT}	{parser.yylval = new NodoArbol(new Double(yytext()), 0);/*new ParserVal( new Double(yytext()) )*/  return Parser.NUMF;}

[ \t\f]	{/*Se ignora espacio */}

. {// token desconocido: se produce un mensaje de error 
          parser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido");}
