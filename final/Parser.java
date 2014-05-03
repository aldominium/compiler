//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 26 "final.y"
  import java.io.*;
  import java.util.*;

  
//#line 22 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short BEGIN=257;
public final static short END=258;
public final static short PRINT=259;
public final static short READ=260;
public final static short EQUALS=261;
public final static short INT=262;
public final static short FLOAT=263;
public final static short OR=264;
public final static short WHILE=265;
public final static short DO=266;
public final static short FUNCTION=267;
public final static short IF=268;
public final static short THEN=269;
public final static short PROGRAM=270;
public final static short VAR=271;
public final static short NUMF=272;
public final static short NUMENT=273;
public final static short ID=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,   16,   16,   16,   18,    1,    1,   17,   17,   17,
   19,   14,   14,   14,   15,    7,    7,    7,    7,    7,
    7,    6,    6,    6,    8,   13,   13,   13,   12,   12,
    9,    9,    9,   10,   10,   11,   11,   11,   11,   11,
   11,    2,    2,    2,    3,    3,    3,    4,    4,    4,
    5,    5,
};
final static short yylen[] = {                            2,
    9,    3,    1,    0,    4,    1,    1,    3,    1,    0,
   10,    3,    1,    0,    3,    3,    4,    4,    3,    2,
    2,    3,    1,    0,    1,    3,    1,    0,    1,    3,
    3,    2,    1,    3,    1,    3,    1,    1,    1,    4,
    2,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    3,    0,    0,    0,
    0,    0,    0,    0,    2,    0,    0,    6,    7,    5,
    0,    0,    0,    0,    0,    0,   25,    0,    0,    0,
    8,    0,    0,    0,    0,   39,   38,    0,    0,    0,
   51,   52,    0,    0,    0,   35,   20,    0,    0,    0,
    0,    0,    0,    0,    0,   19,    0,    0,    0,   41,
    0,   47,   45,   46,    0,   48,   49,   50,    0,    0,
    0,    1,   22,    0,   15,    0,   12,    0,    0,   42,
   43,   44,    0,   36,    0,   34,   18,   17,    0,    0,
   40,    0,    0,   26,    0,   11,
};
final static short yydgoto[] = {                          2,
   20,   83,   65,   69,   43,   28,   29,   30,   78,   45,
   46,   59,   79,   33,   34,    6,   11,    7,   12,
};
final static short yysindex[] = {                      -256,
 -253,    0,  -26, -241, -234,  -54,    0,  -17, -227, -241,
 -208,    3, -239,   30,    0, -165, -198,    0,    0,    0,
 -197, -165,  -33, -187,  -33,  -33,    0, -167,   34, -164,
    0,   38,   57,   40, -157,    0,    0,   64,  -33,  -29,
    0,    0,  -29,   -6,   29,    0,    0,  -30,  -42,   59,
 -165,  -33, -239,   48, -197,    0,  -33,   23,   66,    0,
   29,    0,    0,    0,  -29,    0,    0,    0,  -29, -165,
 -165,    0,    0,   -6,    0, -239,    0,  -27,   67,    0,
    0,    0,  -33,    0,   29,    0,    0,    0, -147,  -33,
    0,   -6, -165,    0, -146,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  -57,    0, -144,    0,    0,    0,    0,
    0, -143,    0,    0,    0, -142, -144,    0,    0,    0,
   70, -142,    0,    0,    0,    0,    0,    0, -141,    0,
    0,    0,    0,   77,    0,    0,    0,  -16,    0,    0,
    0,    0,    0,  -53,   -9,    0,    0,    0,    0,    0,
 -142,    0,    0,    0,   70,    0,   78,   79,    0,    0,
   13,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -51,    0,    0,    0,   80,    0,    0,
    0,    0,    0,    0,   20,    0,    0,    0,    0,   78,
    0,   81, -142,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    2,    0,    0,    0,    0,   -3,  -11,   99,   63,  -23,
  -31,    0,   35,   69,    0,    0,  109,  117,    0,
};
final static int YYTABLESIZE=289;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         40,
   63,    4,   64,   40,   10,   21,   39,   16,   60,   41,
   39,   42,   63,    1,   64,   63,   90,   64,   35,   61,
    3,   37,   18,   19,   37,   37,   37,   37,   37,    5,
   37,   33,    4,   33,   33,   33,   63,   86,   64,    8,
   13,   85,   37,   37,   37,   37,   14,   73,   16,   33,
   33,   33,   33,   32,   75,   32,   32,   32,   87,   88,
   31,   17,   31,   31,   31,   63,   68,   64,    9,   21,
   66,   32,   32,   32,   32,   67,   32,   89,   31,   31,
   31,   31,   80,   81,   82,   44,   27,   48,   49,   95,
   50,   22,   51,   23,   24,   53,   52,   54,   55,   25,
   56,   58,   26,   57,   72,   76,   84,   91,   27,   93,
   14,   96,   10,    9,   74,   24,   23,   13,   28,   29,
   27,   30,   47,   77,   94,   31,   15,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   92,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    4,
    0,    0,    0,    0,   21,    0,   16,    0,    0,    4,
    0,    0,    9,    0,    0,    0,    0,    0,    0,    0,
    0,   62,    0,    0,    0,    0,   71,    0,    0,    0,
    0,    0,    0,   62,    0,   70,   62,    0,   36,   37,
   38,   37,   36,   37,   38,    0,    0,   37,   33,   37,
    0,    0,   37,    0,   33,    0,   33,   62,    0,   33,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   32,    0,    0,    0,    0,    0,   32,   31,   32,    0,
    0,   32,    0,   31,    0,   31,   62,    0,   31,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   43,   59,   45,   33,   59,   59,   40,   59,   40,   43,
   40,   45,   43,  270,   45,   43,   44,   45,   22,   43,
  274,   38,  262,  263,   41,   42,   43,   44,   45,  271,
   47,   41,   59,   43,   44,   45,   43,   69,   45,  274,
   58,   65,   59,   60,   61,   62,  274,   51,  257,   59,
   60,   61,   62,   41,   53,   43,   44,   45,   70,   71,
   41,   59,   43,   44,   45,   43,   38,   45,  267,   40,
   42,   59,   60,   61,   62,   47,  274,   76,   59,   60,
   61,   62,   60,   61,   62,   23,  274,   25,   26,   93,
  258,  257,   59,  259,  260,   58,  261,   41,   59,  265,
  258,   39,  268,   40,   46,   58,   41,   41,  274,  257,
   41,  258,  257,  257,   52,  258,  258,   41,   41,   41,
   41,   41,   24,   55,   90,   17,   10,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   83,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,   -1,   -1,   -1,  258,   -1,  258,   -1,   -1,  267,
   -1,   -1,  267,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  264,   -1,   -1,   -1,   -1,  269,   -1,   -1,   -1,
   -1,   -1,   -1,  264,   -1,  266,  264,   -1,  272,  273,
  274,  258,  272,  273,  274,   -1,   -1,  264,  258,  266,
   -1,   -1,  269,   -1,  264,   -1,  266,  264,   -1,  269,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  258,   -1,   -1,   -1,   -1,   -1,  264,  258,  266,   -1,
   -1,  269,   -1,  264,   -1,  266,  264,   -1,  269,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=274;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,"'&'",null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"BEGIN","END","PRINT","READ","EQUALS","INT",
"FLOAT","OR","WHILE","DO","FUNCTION","IF","THEN","PROGRAM","VAR","NUMF",
"NUMENT","ID",
};
final static String yyrule[] = {
"$accept : prog",
"prog : PROGRAM ID ';' decls fun_decls BEGIN stmt_lst END '.'",
"decls : decls ';' dec",
"decls : dec",
"decls :",
"dec : VAR ID ':' tipo",
"tipo : INT",
"tipo : FLOAT",
"fun_decls : fun_dec ';' fun_decls",
"fun_decls : fun_dec",
"fun_decls :",
"fun_dec : FUNCTION ID '(' params ')' ':' tipo BEGIN stmt_lst END",
"params : param ';' params",
"params : param",
"params :",
"param : ID ':' tipo",
"stmt : var EQUALS expr",
"stmt : IF expr THEN stmt",
"stmt : WHILE expr DO stmt",
"stmt : BEGIN stmt_lst END",
"stmt : READ var",
"stmt : PRINT expr",
"stmt_lst : stmt ';' stmt_lst",
"stmt_lst : stmt",
"stmt_lst :",
"var : ID",
"expr_lst : expr ',' expr_lst",
"expr_lst : expr",
"expr_lst :",
"expresion : expr",
"expresion : expr relop expr",
"expr : expr sumop term",
"expr : signo term",
"expr : term",
"term : term prodop factor",
"term : factor",
"factor : '(' expresion ')'",
"factor : ID",
"factor : NUMENT",
"factor : NUMF",
"factor : ID '(' expr_lst ')'",
"factor : '!' factor",
"relop : '<'",
"relop : '='",
"relop : '>'",
"sumop : '+'",
"sumop : '-'",
"sumop : OR",
"prodop : '*'",
"prodop : '/'",
"prodop : '&'",
"signo : '+'",
"signo : '-'",
};

//#line 287 "final.y"
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
//#line 382 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 78 "final.y"
{
                                          tablaGlobal.imprimir();
                                          System.out.println("program");
                                          nodoPrincipal = new Nodo(null,";",(Nodo)val_peek(2).obj);
                                          }
break;
case 2:
//#line 85 "final.y"
{ }
break;
case 3:
//#line 86 "final.y"
{}
break;
case 4:
//#line 87 "final.y"
{}
break;
case 5:
//#line 90 "final.y"
{ 
                        tablaGlobal.añadirDeclaracionVariable(val_peek(2).sval,val_peek(0).sval);
                          }
break;
case 6:
//#line 95 "final.y"
{
              /*System.out.println("tipo:" + $1);*/
              yyval.sval = "int";


            }
break;
case 7:
//#line 101 "final.y"
{
              /*System.out.println("tipo:");*/
              yyval.sval = "float";
              }
break;
case 8:
//#line 107 "final.y"
{ 

                                  }
break;
case 9:
//#line 110 "final.y"
{}
break;
case 10:
//#line 111 "final.y"
{}
break;
case 11:
//#line 114 "final.y"
{
                                  TablaSimbolos tablaLocal = new TablaSimbolos();
                                  tablaLocal.añadirDeclaracionesVariables((Nodo)val_peek(6).obj);
                                  tablaLocal.añadirVariableFuncion(val_peek(8).sval,val_peek(3).sval);
                                  tablaGlobal.añadirDeclaracionFuncion(val_peek(8).sval,val_peek(3).sval,numParams,tablaLocal,new Nodo(null,";",(Nodo)val_peek(1).obj));
                                  numParams = 0;
                                  /*tablaLocal.imprimir();*/
                                  System.out.println();
                                  }
break;
case 12:
//#line 125 "final.y"
{
                    yyval.obj = new Nodo((Nodo)val_peek(2).obj,";",(Nodo)val_peek(0).obj);
                  }
break;
case 13:
//#line 128 "final.y"
{
              yyval.obj = new Nodo((Nodo)val_peek(0).obj,";",null);
            }
break;
case 14:
//#line 131 "final.y"
{
      
            }
break;
case 15:
//#line 136 "final.y"
{
                    yyval.obj = new Nodo(new Nodo("ID",val_peek(2).sval),":",new Nodo("tipo",val_peek(0).sval));
                    numParams ++;
                  }
break;
case 16:
//#line 144 "final.y"
{
                          yyval.obj = new Nodo(new Nodo("ID",val_peek(2).sval),":=",(Nodo)val_peek(0).obj);

                      }
break;
case 17:
//#line 148 "final.y"
{
                          yyval.obj = new Nodo((Nodo)val_peek(2).obj,"IF",(Nodo)val_peek(0).obj);
                              }
break;
case 18:
//#line 151 "final.y"
{
                          yyval.obj = new Nodo((Nodo)val_peek(2).obj,"WHILE",(Nodo)val_peek(0).obj);
                                }
break;
case 19:
//#line 154 "final.y"
{
                          yyval.obj = val_peek(1).obj;
                          }
break;
case 20:
//#line 157 "final.y"
{
                  yyval.obj = new Nodo("READ",new Nodo("ID",val_peek(0).sval));
                }
break;
case 21:
//#line 160 "final.y"
{
                  yyval.obj = new Nodo("PRINT",(Nodo)val_peek(0).obj);
                  }
break;
case 22:
//#line 166 "final.y"
{
                                yyval.obj = new Nodo((Nodo)val_peek(2).obj,";",(Nodo)val_peek(0).obj);
                              }
break;
case 23:
//#line 169 "final.y"
{
                  yyval.obj = new Nodo((Nodo)val_peek(0).obj,";",null);/*$$ =new Nodo((Nodo)$1,";",null);*/
                }
break;
case 24:
//#line 172 "final.y"
{
            /*System.out.println("vacio");*/
           }
break;
case 25:
//#line 177 "final.y"
{
          yyval.sval = val_peek(0).sval;
          }
break;
case 26:
//#line 182 "final.y"
{
                              yyval.obj = new Nodo((Nodo)val_peek(2).obj,",",(Nodo)val_peek(0).obj);
                            }
break;
case 27:
//#line 185 "final.y"
{
                yyval.obj = new Nodo((Nodo)val_peek(0).obj,",",null);
              }
break;
case 28:
//#line 188 "final.y"
{

          }
break;
case 29:
//#line 193 "final.y"
{
                  yyval.obj = (Nodo)val_peek(0).obj;
                  }
break;
case 30:
//#line 196 "final.y"
{
                            yyval.obj = new Nodo((Nodo)val_peek(2).obj,val_peek(1).sval,(Nodo)val_peek(0).obj);
                          }
break;
case 31:
//#line 202 "final.y"
{
                        yyval.obj = new Nodo((Nodo)val_peek(2).obj,val_peek(1).sval,(Nodo)val_peek(0).obj);
                      }
break;
case 32:
//#line 205 "final.y"
{
                    yyval.obj = new Nodo("signo",(Nodo)val_peek(0).obj);
                  }
break;
case 33:
//#line 208 "final.y"
{
          yyval.obj = val_peek(0).obj;
          }
break;
case 34:
//#line 214 "final.y"
{
                        yyval.obj = new Nodo((Nodo)val_peek(2).obj,val_peek(1).sval,(Nodo)val_peek(0).obj);
  
                        }
break;
case 35:
//#line 218 "final.y"
{
          yyval.obj = val_peek(0).obj;
      }
break;
case 36:
//#line 223 "final.y"
{
                          yyval.obj = val_peek(1).obj;
  
                            }
break;
case 37:
//#line 227 "final.y"
{
                        yyval.obj = new Nodo("ID",val_peek(0).sval);/*talvez ponga variable*/
          }
break;
case 38:
//#line 230 "final.y"
{
                      yyval.obj = new Nodo("NUMENT",val_peek(0).dval);
              }
break;
case 39:
//#line 233 "final.y"
{
                  yyval.obj = new Nodo("NUMF",val_peek(0).dval);
            }
break;
case 40:
//#line 236 "final.y"
{
                            yyval.obj = new Nodo(new Nodo("ID",val_peek(3).sval),"call",(Nodo)val_peek(1).obj);
                            }
break;
case 41:
//#line 239 "final.y"
{
                          yyval.obj = new Nodo("!",(Nodo)val_peek(0).obj);
                    }
break;
case 42:
//#line 243 "final.y"
{
                  yyval.sval = "<";
                  }
break;
case 43:
//#line 246 "final.y"
{
                yyval.sval = "=";
            }
break;
case 44:
//#line 249 "final.y"
{
              yyval.sval = ">";
            }
break;
case 45:
//#line 254 "final.y"
{
              yyval.sval = "+";
                  }
break;
case 46:
//#line 257 "final.y"
{
            yyval.sval = "-";
            }
break;
case 47:
//#line 260 "final.y"
{
            yyval.sval = "||";
            }
break;
case 48:
//#line 265 "final.y"
{
              yyval.sval = "*";
              }
break;
case 49:
//#line 268 "final.y"
{
            yyval.sval = "/";
            }
break;
case 50:
//#line 271 "final.y"
{
            yyval.sval = "&";
            }
break;
case 51:
//#line 276 "final.y"
{
            yyval.sval = "+";
            }
break;
case 52:
//#line 279 "final.y"
{
            yyval.sval = "-";
            }
break;
//#line 849 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
