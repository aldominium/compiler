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

NUM = [0-9]+ ("." [0-9]+)?
ID = [a-zA-Z]+
NL  = \n | \r | \r\n

%%

/* operators */
"+" | 
"-" | 
"*" | 
"/" |
";" | 
"$" |
"<" |
">" |
"=" |
"^" | 
"(" | 
")"    {
		return (int) yycharat(0);}

/*reserved words*/
"begin"    {return Parser.BEGIN;}
"end"      {return Parser.END;}
"print"    {return Parser.PRINT;}
"read"     {return Parser.READ;}
":="     {return Parser.EQUALS;}


/*-----------*/
/* newline */
{NL}   { return Parser.NL; }

/* float */
{NUM}  { yyparser.yylval = new ParserVal(Double.parseDouble(yytext()));
         return Parser.NUM; }
{ID}   {yyparser.yylval = new ParserVal(yytext());
         return Parser.ID;}

/* whitespace */
[ \t]+ { }

\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }


