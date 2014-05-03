/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
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

%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

ID = [a-zA-Z]+([0-9])*
NUMENT = [0-9][0-9]*
NUMF  = [0-9][0-9]*\.[0-9][0-9]*//[-+]?([0-9]*\.[0-9]+|[0-9]+)

%%

/* operators */
"+" |
"!" |
":" |
"&" | 
"-" | 
"*" | 
"/" |
";" |
"," |  
"$" |
"<" |
">" |
"=" |
"^" | 
"(" |
"." | 
")"    {
		return (int) yycharat(0);}

/*reserved words*/
\r|\n|\r\n {/*Se ignora retorno de carro*/}

"begin"    {return Parser.BEGIN;}
"end"      {return Parser.END;}
"print"    {return Parser.PRINT;}
"read"     {return Parser.READ;}
":="     {return Parser.EQUALS;}
"int"     {return Parser.INT;}
"float"     {return Parser.FLOAT;}
"||"     {return Parser.OR;}
"while"     {return Parser.WHILE;}
"do"     {return Parser.DO;}
"function"     {return Parser.FUNCTION;}
"if"     {return Parser.IF;}
"then"     {return Parser.THEN;}
"program"     {return Parser.PROGRAM;}
"var"     {return Parser.VAR;}


/*-----------*/
/* newline */

/* float */
{NUMENT}  {yyparser.yylval = new ParserVal(Double.parseDouble(yytext()));
         return Parser.NUMENT; }
{NUMF}  {yyparser.yylval = new ParserVal(Double.parseDouble(yytext()));
         return Parser.NUMF; }

{ID}   {yyparser.yylval = new ParserVal(yytext());
         return Parser.ID;}

/* whitespace */
[ \t]+ { }

\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }


