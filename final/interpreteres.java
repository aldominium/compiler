/*
Clase de ayuda para recorrer el arbol sintáctico
Autor:Aldo Olivares Domínguez
@aldominium
*/
import java.util.Scanner;
public class Interprete{

	TablaSimbolos tablaGlobal;
	public final double TRUE = 1;
	public final double FALSE = 0;


	//Se crea el interprete y se le pasa la tabla de simbolos
	//global creada desde el parser
	public Interprete(TablaSimbolos tablaGlobal){
		this.tablaGlobal = tablaGlobal;
	}

	double parseExpr(Nodo nodo,String funId){
		switch (nodo.getKind()) {
			case "ID":
				if (funId == null) {//Llamada desde el cuerpo principal del programa
					return (double)tablaGlobal.obtenerValorVariable(nodo.getID());
				}else{
					//Estamos dentro de una funcion

					TablaSimbolos tablaLocal = tablaGlobal.obtenerTablaLocal(funId);
					if (tablaLocal.existeVariable(nodo.getID())) {
						return (double)tablaLocal.obtenerValorVariable(nodo.getID());
					}else{
						return (double)tablaGlobal.obtenerValorVariable(nodo.getID());
					}

				}
			case "NUMENT":
				return (double)nodo.getValor();
			case "NUMF":
				return (double)nodo.getValor();
			case "*":
				return parseExpr(nodo.getLeftChild(),funId) * parseExpr(nodo.getRightChild(),funId);
			case "/":
				return parseExpr(nodo.getLeftChild(),funId) / parseExpr(nodo.getRightChild(),funId);
			case "+":
				return parseExpr(nodo.getLeftChild(),funId) + parseExpr(nodo.getRightChild(),funId);
			case "-":
				return parseExpr(nodo.getLeftChild(),funId) - parseExpr(nodo.getRightChild(),funId);
			case "=":
			case "<":
			case ">":
				return parseOpRel(nodo,funId);
			case "||":
			case "&":
				return parseOpLog(nodo,funId);
			case "call":
				return parseFun(nodo,funId);
			default:
             throw new IllegalArgumentException("Invalid expresion: " + nodo.getKind());
		}
	}

	double parseOpRel(Nodo nodo, String funId){
		switch (nodo.getKind()) {
			case "<":
				if (parseExpr(nodo.getLeftChild(),funId) < parseExpr(nodo.getRightChild(),funId)) {
					return TRUE;
				}else{
					return FALSE;
				}
			
			case ">":
				if (parseExpr(nodo.getLeftChild(),funId) > parseExpr(nodo.getRightChild(),funId)) {
					return TRUE;
				}else{
					return FALSE;
				}
			
			case "=":
				if (parseExpr(nodo.getLeftChild(),funId) == parseExpr(nodo.getRightChild(),funId)) {
					return TRUE;
				}else{
					return FALSE;
				}
			
			default:
             throw new IllegalArgumentException("Invalid Oprel: " + nodo.getKind());
			
		}
	}

	double parseOpLog(Nodo nodo, String funId){
		switch (nodo.getKind()) {
			case "||":
				if ((parseExpr(nodo.getLeftChild(),funId) != 0) || parseExpr(nodo.getRightChild(),funId) != 0) {
					return TRUE;
				}else{
					return FALSE;
				}
			case "&":
				if ((parseExpr(nodo.getLeftChild(),funId) != 0) && parseExpr(nodo.getRightChild(),funId) != 0) {
					return TRUE;
				}else{
					return FALSE;
				}
			default:
             throw new IllegalArgumentException("Invalid OpLog: " + nodo.getKind());
			
		}

	}

	void parseAsig(Nodo nodo,String funId){

		String nombreVariable = nodo.getLeftChild().getID();
		
		if (funId == null) {
			//System.out.println("fun null de asig");
			tablaGlobal.cambiarValorVariable(nombreVariable,parseExpr(nodo.getRightChild(),funId));
		
		}else {//estamos en una funcion

			
			TablaSimbolos tablaLocal = tablaGlobal.obtenerTablaLocal(funId);
			
			if (nombreVariable.equals(funId)) {//Estas asignando el valor de una funcion,
											   //se cambia en Tl y global
				tablaGlobal.cambiarValorFuncion(nombreVariable,parseExpr(nodo.getRightChild(),funId));
				tablaLocal.cambiarValorVariable(nombreVariable,parseExpr(nodo.getRightChild(),funId));
				
			}else{//Es asignacion ordinaria
				if (tablaLocal.existeVariable(nombreVariable)) {
					tablaLocal.cambiarValorVariable(nombreVariable,parseExpr(nodo.getRightChild(),funId));
				}else if (tablaGlobal.existeVariable(nombreVariable)) {
					tablaGlobal.cambiarValorVariable(nombreVariable,parseExpr(nodo.getRightChild(),funId));
				}
			}
			
		}
		//System.out.println("termine parse asig sin problemas");

	}
	
	void parsePrint(Nodo nodo,String funId){
		//No estamos dentro de una funcion
		if (funId == null) {
			if (nodo.getLeftChild().getKind() == "ID") {
				System.out.println(nodo.getLeftChild().getID() + " vale : "+tablaGlobal.obtenerValorVariable(nodo.getLeftChild().getID()));
			}else{
				System.out.println("La expresión es igual a: " + parseExpr(nodo.getLeftChild(),funId));
			}
		}else{//Estamos dentro de una funcion
			TablaSimbolos tablaLocal = tablaGlobal.obtenerTablaLocal(funId);
			if (nodo.getLeftChild().getKind() == "ID") {
				if (tablaLocal.existeVariable(nodo.getLeftChild().getID())) {
					System.out.println(nodo.getLeftChild().getID() + " vale : "+tablaLocal.obtenerValorVariable(nodo.getLeftChild().getID()));
				}else{
					System.out.println(nodo.getLeftChild().getID() + " vale : "+tablaGlobal.obtenerValorVariable(nodo.getLeftChild().getID()));
				}
			}else{
				System.out.println("La expresión es igual a: " + parseExpr(nodo.getLeftChild(),funId));
			}

		}

	}
	
	
	//Toma casa stmt y lo manda a la funcion adecuada de acuerdo al tipo
	void parseStmt(Nodo nodo,String funId){
		//nodo.imprimir();
		//System.out.println();
		switch (nodo.getKind()){
		case ":=":
			//System.out.println("asig funId:" + funId);
			parseAsig(nodo,funId);
			break;
		case "IF":
			parseIf(nodo,funId);
			break;
		case "WHILE":
			parseWhile(nodo,funId);
			break;	
		case "PRINT":
			parsePrint(nodo,funId);
			break;
		case "READ":
			parseRead(nodo,funId);
			break;
		default:
            throw new IllegalArgumentException("Invalid statement: " + nodo.getKind() + " valor: " + nodo.getValor());
			
		}
	}

	void parseStmtLst(Nodo nodo,String funId){
		if(nodo != null){
			
			Nodo nodoActual = nodo;
			parseStmt(nodoActual.getLeftChild(),funId);
			
			while(!nodoActual.isLast()){
				nodoActual = nodoActual.getRightChild();
				parseStmt(nodoActual.getLeftChild(),funId);
				
			}
			
		}
	}

	double parseFun(Nodo nodo, String funId){

	
		Nodo nodoLocal;
		TablaSimbolos tablaLocal;
		nodoLocal = tablaGlobal.obtenerArbolLocal(funId);
		tablaLocal = tablaGlobal.obtenerTablaLocal(funId);
		

		parseParams(nodo.getRightChild(),funId);
		parseStmtLst(nodoLocal.getRightChild(),funId);
		return (double)tablaGlobal.obtenerValorFuncion(funId);
	}

	void parseParams(Nodo nodo, String funId){
		int numeroParam = 0;
		if(nodo != null){
			
			Nodo nodoActual = nodo;
			parseParam(nodoActual.getLeftChild(),funId,numeroParam);
			
			while(!(nodoActual.getRightChild() == null)){
				numeroParam++;
				nodoActual = nodoActual.getRightChild();
				parseParam(nodoActual.getLeftChild(),funId,numeroParam);
				
			}
			
		}
	}

	void parseParam(Nodo nodo, String funId int numeroParam){
		
			



			TablaSimbolos tablaLocal = tablaGlobal.obtenerTablaLocal(funId);
			tablaLocal.valor.set(numeroParam,parseExpr(nodo,funId));


		
	}
	
	void parseIf(Nodo nodo,String funId){
		

		if(parseExpr(nodo.getLeftChild(),funId) != 0){
			if((nodo.getRightChild().getKind().equals(";"))){
				parseStmtLst(nodo.getRightChild(),funId);
			}
			else{
				parseStmt(nodo.getRightChild(),funId);
			}
		}
	//System.out.println("Parse if sin problema");
	}

	void parseWhile(Nodo nodo, String funId){

		while(parseExpr(nodo.getLeftChild(),funId) != 0){
			if((nodo.getRightChild().getKind().equals(";")))
				parseStmtLst(nodo.getRightChild(),funId);
			else{
				parseStmt(nodo.getRightChild(),funId);
			}
		}
	}

	void parseRead(Nodo nodo,String funId){
		String id = nodo.getLeftChild().getID();
		Scanner in = new Scanner(System.in);
		System.out.println("Inserta valor de " + id + ": ");
        Double miValor = in.nextDouble();
        

			TablaSimbolos tablaLocal = tablaGlobal.obtenerTablaLocal(id);

			if (tablaLocal.existeVariable(nodo.getLeftChild().getID())) {
				tablaLocal.cambiarValorVariable(id,miValor);
			}else{
				tablaGlobal.cambiarValorVariable(id,miValor);			
			}

		}
	}

	void parseProgram(Nodo nodo){
		//Parse program llame a fun ID con null, ya que no es parte de una funcion
		parseStmtLst(nodo,null);
		System.out.println("Ejecucion Terminada");
	}

}