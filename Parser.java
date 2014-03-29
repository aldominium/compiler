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






//#line 26 "ejemplo2.y"
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
public final static short NL=257;
public final static short BEGIN=258;
public final static short END=259;
public final static short PRINT=260;
public final static short READ=261;
public final static short EQUALS=262;
public final static short IF=263;
public final static short THEN=264;
public final static short NUM=265;
public final static short ID=266;
public final static short NEG=267;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    3,    3,    3,    3,    3,    4,    4,    2,    2,
    2,    1,    1,    1,    5,    5,    5,    6,    6,    6,
};
final static short yylen[] = {                            2,
    3,    3,    4,    3,    2,    2,    3,    0,    3,    3,
    3,    3,    3,    1,    3,    3,    1,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   20,   19,    0,    0,    0,   17,    6,    0,    0,    0,
    0,    1,    4,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    7,   18,    0,    0,   15,   16,    0,
    0,    0,    3,
};
final static short yydgoto[] = {                          2,
   14,   19,    8,    9,   15,   16,
};
final static short yysindex[] = {                      -253,
 -217,    0, -217,  -40, -256,  -40, -236,  -22, -223, -220,
    0,    0,  -40,  -28,  -35,    0,    0,   -3, -213,  -40,
 -217,    0,    0,    7,  -40,  -40,  -40,  -40,  -40,  -40,
  -40, -217,  -28,    0,    0,  -35,  -35,    0,    0,  -28,
  -28,  -28,    0,
};
final static short yyrindex[] = {                         0,
 -212,    0, -212,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -6,  -37,    0,    0,    0,    0,    0,
 -212,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,  -32,  -27,    0,    0, -203,
 -202, -201,    0,
};
final static short yygindex[] = {                         0,
   25,    0,   32,   -2,  -23,   -7,
};
final static int YYTABLESIZE=237;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         13,
   10,   36,   37,   14,    1,   14,   27,   14,   13,   17,
   13,   28,   13,   12,   26,   12,   25,   12,   34,   38,
   39,   14,   14,   14,   14,   20,   13,   13,   13,   13,
   18,   12,   12,   12,   12,   22,   21,   24,   23,   26,
    3,   25,    4,    5,   33,    6,    8,   35,    7,   26,
   32,   25,    5,   40,   41,   42,   29,   31,   30,    2,
    9,   10,   11,   43,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   11,   12,   14,    0,    0,    0,
    0,   13,    0,    0,    0,    0,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    3,   25,   26,   41,  258,   43,   42,   45,   41,  266,
   43,   47,   45,   41,   43,   43,   45,   45,   21,   27,
   28,   59,   60,   61,   62,  262,   59,   60,   61,   62,
    6,   59,   60,   61,   62,  259,   59,   13,  259,   43,
  258,   45,  260,  261,   20,  263,  259,   41,  266,   43,
  264,   45,   59,   29,   30,   31,   60,   61,   62,   59,
  264,  264,  264,   32,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  265,  266,  264,   -1,   -1,   -1,
   -1,  264,   -1,   -1,   -1,   -1,  264,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=267;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'",null,
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'^'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"NL","BEGIN","END","PRINT","READ","EQUALS",
"IF","THEN","NUM","ID","NEG",
};
final static String yyrule[] = {
"$accept : prg",
"prg : BEGIN stmt_lst END",
"stmt : ID EQUALS expr",
"stmt : IF expresion THEN stmt",
"stmt : BEGIN stmt_lst END",
"stmt : PRINT expr",
"stmt : READ ID",
"stmt_lst : stmt ';' stmt_lst",
"stmt_lst :",
"expresion : expr '<' expr",
"expresion : expr '>' expr",
"expresion : expr '=' expr",
"expr : expr '+' term",
"expr : expr '-' term",
"expr : term",
"term : term '*' factor",
"term : term '/' factor",
"term : factor",
"factor : '(' expr ')'",
"factor : ID",
"factor : NUM",
};

//#line 136 "ejemplo2.y"
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
//#line 323 "Parser.java"
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
//#line 66 "ejemplo2.y"
{ 
                            System.out.println("prg");
                           nodoInicial = new Nodo(null,";",(Nodo)val_peek(1).obj);
                          }
break;
case 2:
//#line 72 "ejemplo2.y"
{
                              simbolos.put(val_peek(2).sval,0.0);
                              System.out.println("stmt");
                              yyval.obj = new Nodo(new Nodo("ID",val_peek(2).sval),":=",(Nodo)val_peek(0).obj);
                             }
break;
case 3:
//#line 77 "ejemplo2.y"
{
                              yyval.obj = new Nodo((Nodo)val_peek(2).obj,"IF",(Nodo)val_peek(0).obj);
                             }
break;
case 4:
//#line 80 "ejemplo2.y"
{
                              yyval.obj = (Nodo)val_peek(1).obj;
                             }
break;
case 5:
//#line 83 "ejemplo2.y"
{
                              System.out.println("stmt");
                              yyval.obj = new Nodo("PRINT",(Nodo)val_peek(0).obj);
                             }
break;
case 6:
//#line 87 "ejemplo2.y"
{
                              simbolos.put(val_peek(0).sval,0.0);
                              yyval.obj = new Nodo("READ",new Nodo("ID",val_peek(0).sval));
                             }
break;
case 7:
//#line 94 "ejemplo2.y"
{
                              System.out.println("stmt_lst");
                              yyval.obj = new Nodo((Nodo)val_peek(2).obj,";",(Nodo)val_peek(0).obj);
                             }
break;
case 8:
//#line 98 "ejemplo2.y"
{yyval.obj = null;}
break;
case 9:
//#line 102 "ejemplo2.y"
{yyval.obj = new Nodo((Nodo)val_peek(2).obj,"<",(Nodo)val_peek(0).obj);}
break;
case 10:
//#line 103 "ejemplo2.y"
{yyval.obj = new Nodo((Nodo)val_peek(2).obj,">",(Nodo)val_peek(0).obj);}
break;
case 11:
//#line 104 "ejemplo2.y"
{yyval.obj = new Nodo((Nodo)val_peek(2).obj,"=",(Nodo)val_peek(0).obj);}
break;
case 12:
//#line 108 "ejemplo2.y"
{yyval.obj = new Nodo((Nodo)val_peek(2).obj,"+",(Nodo)val_peek(0).obj);}
break;
case 13:
//#line 109 "ejemplo2.y"
{yyval.obj = new Nodo((Nodo)val_peek(2).obj,"-",(Nodo)val_peek(0).obj);}
break;
case 14:
//#line 110 "ejemplo2.y"
{System.out.println("entre expr");
                              yyval.obj = val_peek(0).obj;
                              }
break;
case 15:
//#line 116 "ejemplo2.y"
{yyval.obj = new Nodo((Nodo)val_peek(2).obj,"*",(Nodo)val_peek(0).obj);}
break;
case 16:
//#line 117 "ejemplo2.y"
{yyval.obj = new Nodo((Nodo)val_peek(2).obj,"/",(Nodo)val_peek(0).obj);}
break;
case 17:
//#line 118 "ejemplo2.y"
{System.out.println("entre term");
                              yyval.obj = val_peek(0).obj;
                             }
break;
case 18:
//#line 124 "ejemplo2.y"
{yyval.obj = (Nodo)val_peek(1).obj;}
break;
case 19:
//#line 125 "ejemplo2.y"
{
                              System.out.println("factor id" + val_peek(0).sval);
                              simbolos.put(val_peek(0).sval,0.0);
                              yyval.obj = new Nodo("ID",val_peek(0).sval);
                             }
break;
case 20:
//#line 130 "ejemplo2.y"
{
                              System.out.println("factor num");
                              yyval.obj = new Nodo("NUM",val_peek(0).dval);
                              }
break;
//#line 583 "Parser.java"
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
