import java.util.Hashtable;
import java.util.Scanner;

/*
Clase de ayuda para recorrer el arbol sintáctico
Autor:Aldo Olivares Domínguez
@aldominium
*/
public class Interprete{

	double parseExpr(Nodo nodo,Hashtable<String, Double> simbolos){
		switch (nodo.getKind()) {
			case "ID":
				return simbolos.get(nodo.getID());
				
			case "NUM":
				return nodo.getValor();
				
			case "*":
				return parseExpr(nodo.getLeftChild(),simbolos) * parseExpr(nodo.getRightChild(),simbolos);
				
			case "/":
				return parseExpr(nodo.getLeftChild(),simbolos) / parseExpr(nodo.getRightChild(),simbolos);
				
			case "+":
				return parseExpr(nodo.getLeftChild(),simbolos) + parseExpr(nodo.getRightChild(),simbolos);
				
			case "-":
				return parseExpr(nodo.getLeftChild(),simbolos) - parseExpr(nodo.getRightChild(),simbolos);
				
			default:
             throw new IllegalArgumentException("Invalid expresion: " + nodo.getKind());
		}
	}

	void parseAsig(Nodo nodo,Hashtable<String, Double> simbolos){
		String id = nodo.getLeftChild().getID();
		simbolos.put(id, parseExpr(nodo.getRightChild(),simbolos));
	}
	
	void parsePrint(Nodo nodo,Hashtable<String, Double> simbolos){
		//System.out.println(nodo.getLeftChild().getID() + " vale : "+simbolos.get(nodo.getLeftChild().getID()));
		if (nodo.getLeftChild().getKind() == "ID") {
			System.out.println(nodo.getLeftChild().getID() + " vale : "+simbolos.get(nodo.getLeftChild().getID()));
		}else{
			System.out.println("La expresión es igual a: " + parseExpr(nodo.getLeftChild(),simbolos));
		}
	}
	
	boolean parseExpresion(Nodo nodo, Hashtable<String, Double> simbolos){
		
		switch (nodo.getKind()){
		case "<":
			return parseExpr(nodo.getLeftChild(),simbolos) < parseExpr(nodo.getRightChild(),simbolos);
			
		case ">":
			return parseExpr(nodo.getLeftChild(),simbolos) > parseExpr(nodo.getRightChild(),simbolos);
			
		case "=":
			return parseExpr(nodo.getLeftChild(),simbolos) == parseExpr(nodo.getRightChild(),simbolos);
			
		default:
            throw new IllegalArgumentException("Invalid comparision: " + nodo.getKind());
		
		}
		
	}
	
	void parseStmt(Nodo nodo,Hashtable<String, Double> simbolos){
		switch (nodo.getKind()){
		case ":=":
			parseAsig(nodo,simbolos);
			break;
		case "IF":
			parseIf(nodo,simbolos);
			break;
		case "PRINT":
			parsePrint(nodo,simbolos);
			break;
		case "READ":
			parseRead(nodo,simbolos);
			break;
		default:
            throw new IllegalArgumentException("Invalid statement: " + nodo.getKind());
			
		}
	}
	
	void parseStmtLst(Nodo nodo,Hashtable<String, Double> simbolos){
		if(nodo != null){
			
			Nodo nodoActual = nodo;
			parseStmt(nodoActual.getLeftChild(),simbolos);
			
			while(!nodoActual.isLast()){
				nodoActual = nodoActual.getRightChild();
				parseStmt(nodoActual.getLeftChild(),simbolos);
				
			}
			
		}
	}
	
	void parseIf(Nodo nodo,Hashtable<String, Double> simbolos){
		if(parseExpresion(nodo.getLeftChild(),simbolos)){
			//parseStmtLst(nodo.getRightChild(),simbolos);
			if((nodo.getRightChild().getKind().equals(";")))
				parseStmtLst(nodo.getRightChild(),simbolos);
			else{
				parseStmt(nodo.getRightChild(),simbolos);
			}
		}
	}

	void parseRead(Nodo nodo,Hashtable<String, Double> simbolos){
		String id = nodo.getLeftChild().getID();
		Scanner in = new Scanner(System.in);
		System.out.println("Inserta valor de " + id + ": ");
        Double miValor = in.nextDouble();
        simbolos.put(id, miValor);
	}

	void parseProgram(Nodo nodo,Hashtable<String, Double> simbolos){
		parseStmtLst(nodo,simbolos);
		System.out.println("Ejecucion Terminada");
	}

}