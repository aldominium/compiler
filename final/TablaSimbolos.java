import java.util.ArrayList;


public class TablaSimbolos {
	public ArrayList<String> id;
	public ArrayList<String> tipoId;
	public ArrayList<String> tipo;
	public ArrayList<Double> valor;
	public ArrayList<Integer> numParams;
	public ArrayList<TablaSimbolos> tablaSimbolosLocal;
	public ArrayList<Nodo> nodoFuncion;
	
	
	
	
	
	
	public TablaSimbolos(){
		id = new ArrayList<String>();
		tipoId = new ArrayList<String>();
		tipo = new ArrayList<String>();
		valor = new ArrayList<Double>();
		numParams = new ArrayList<Integer>();
		tablaSimbolosLocal = new ArrayList<TablaSimbolos>();
		nodoFuncion = new ArrayList<Nodo>();
		
		
	}
	
	public void añadirVariable(String id,String tipoId,String tipo,double 
							  valor)
	{
		if(!existeVariable(id)){
			this.id.add(id);
			this.tipoId.add(tipoId);
			this.tipo.add(tipo);
			this.valor.add(valor);
			this.numParams.add(null);
			this.tablaSimbolosLocal.add(null);
			this.nodoFuncion.add(null);
		}
		
		
	}

	//Añade declaraciones de variables en base a los nodos de 
	//un arbol
	public void añadirDeclaracionesVariables(Nodo nodo){
		if (nodo != null) {
			Nodo nodoActual = nodo;
			String id = nodoActual.getLeftChild().getLeftChild().getID();
			String tipo = nodoActual.getLeftChild().getRightChild().getID();
			añadirDeclaracionVariable(id,tipo);
			if (!nodoActual.isLast()) {
				nodoActual = nodoActual.getRightChild();
				añadirDeclaracionesVariables(nodoActual);
			}
		}
	}
	
	public void añadirDeclaracionVariable(String id,String tipo)
	{
		if(!existeVariable(id)){
			this.id.add(id);
			this.tipoId.add("var");
			this.tipo.add(tipo);
			this.valor.add(0.0);
			this.numParams.add(null);
			this.tablaSimbolosLocal.add(null);
			this.nodoFuncion.add(null);
		}
	}
	//Cuando es la declaracion de una funcion, se permite que haya el mismo ID
	//por eso creo esta funcion sin esa restriccion
	public void añadirVariableFuncion(String id,String tipo)
	{
		this.id.add(id);
		this.tipoId.add("var");
		this.tipo.add(tipo);
		this.valor.add(0.0);
		this.numParams.add(null);
		this.tablaSimbolosLocal.add(null);
		this.nodoFuncion.add(null);

	}
		
	public void añadirDeclaracionFuncion(String id,String tipo,int numParams,
											TablaSimbolos tablaSimbolosLocal,Nodo nodoFuncion)
	{
		if(!existeFuncion(id)){
			this.id.add(id);
			this.tipoId.add("fun");
			this.tipo.add(tipo);
			this.valor.add(0.0);
			this.numParams.add(numParams);
			this.tablaSimbolosLocal.add(tablaSimbolosLocal);
			this.nodoFuncion.add(nodoFuncion);
		}
	

	}
	
	public void añadirFuncion(String id,String tipoId,String tipo,double 
			  valor,int numParams,TablaSimbolos tablaSimbolosLocal,Nodo nodoFuncion)
	{
		if(!existeFuncion(id)){
			this.id.add(id);
			this.tipoId.add(tipoId);
			this.tipo.add(tipo);
			this.valor.add(valor);
			this.numParams.add(numParams);
			this.tablaSimbolosLocal.add(tablaSimbolosLocal);
			this.nodoFuncion.add(nodoFuncion);
		}
	
	
	}
	
	public void imprimir(){
		String primerNodo;
		
		for(int i = 0; i<this.id.size(); i++){
			System.out.print(this.id.get(i) + " " + this.tipoId.get(i) + " "
							  + this.tipo.get(i) + " "+ this.valor.get(i) + " " + 
							  this.numParams.get(i) + " ");
			if (nodoFuncion.get(i) != null) {
				if (nodoFuncion.get(i).getRightChild() != null) {
					primerNodo = nodoFuncion.get(i).getRightChild().getLeftChild().
					getKind();
					System.out.println("primer nodo \" " + primerNodo + " \"");
				}
			}
			if (tablaSimbolosLocal.get(i) != null) {
				System.out.println("Tabla local: ");
				tablaSimbolosLocal.get(i).imprimir();
			}
			System.out.println();
			
		}
	}
	
	/*
	 * 
	 * Metodos relacionados con las variables
	 * 
	 * 
	 * */
	 
	public int obtenerNumeroVariables(){
		int size = this.tipoId.size();
		int numeroVariables = 0;
		for(int i = 0; i<size;i++){
			if(this.tipoId.get(i).equals("var")){
				numeroVariables++;
			}
		}
		return numeroVariables;
	}
	
	public void cambiarValorVariable(String id,double valor){
		if(existeVariable(id)){
			this.valor.set(obtenerPosicionVariable(id), valor);
		}else{
			System.out.println("CVV No existe esa variable: " + id);
			System.exit(-1);
		}
	}
	
	public Number obtenerValorVariable(String id) {
		
		if(existeVariable(id)){
			return this.valor.get(obtenerPosicionVariable(id));
		}else{

			System.out.println(" OVV No existe esa variable: " + id);
			System.exit(-1);
			return -1;
		}
				
			
		//return -1;
	}
	
	public int obtenerPosicionVariable(String id){
		
		if(existeVariable(id)){
			for(int i = 0;i<obtenerNumeroVariables();i++){
				if(this.id.get(i).equals(id))
					return i;
			}
		}
		else{
			System.out.println("No existe esa funcion");
			System.exit(-1);
		}
		
		return -1;
		
	}
	
	public boolean existeVariable(String id){
		boolean contieneVariable = false;
		for(int i = 0; i<obtenerNumeroVariables();i++){
			if(this.id.get(i).equals(id)){
				contieneVariable = true;
				break;
			}
		}
		return contieneVariable;
		
	}
	
	public String obtenerTipoVariable(String id){
		if(existeVariable(id)){
			return this.tipo.get(obtenerPosicionVariable(id));
		}
		else{
			return null;
		}
	}
	
	/*
	 * Aqui terminar los metodos relacionados con las variables
	 */
	
	public boolean existeFuncion(String id){
		for(int i = obtenerNumeroVariables(); i<this.tipoId.size();i++){
			if(this.id.get(i).equals(id)){
				return true;		
			}		
		}	
		return false;	
	}

	public boolean existeFuncion(String id,int numeroParametros){
		for(int i = obtenerNumeroVariables(); i<this.tipoId.size();i++){
			if(this.id.get(i).equals(id) && this.numParams.get(i) == numeroParametros){
				return true;		
			}		
		}	
		return false;	
	}
	
	public void cambiarValorFuncion(String id,double valor){
		if(existeFuncion(id)){
			this.valor.set(obtenerPosicionFuncion(id), valor);
		}else{
			System.out.println("No existe esa funcion");
			System.exit(-1);
		}
	}
	
	public int obtenerPosicionFuncion(String id){
		if(existeFuncion(id)){
			for(int i = obtenerNumeroVariables();i<this.tipo.size();i++){
				if(this.id.get(i).equals(id)){
					return i;		
				}
			}
		}else{
			System.out.println("No existe esa funcion");
			System.exit(-1);
		}
		return -1;
	}
	
	public String obtenerTipoFuncion(String id){
		if(existeFuncion(id)){
			return this.tipo.get(obtenerPosicionFuncion(id));
		}
		else{
			return null;
		}
	}
	
	public Number obtenerValorFuncion(String id){
		
		if(existeFuncion(id)){
			return this.valor.get(obtenerPosicionFuncion(id));
		}else{
			System.out.println("No existe esa funcion");
			System.exit(-1);
			return -1;
		}
				
	}
	
	public int obtenerNumeroFunciones(){
		int size = this.tipoId.size();
		int numeroFunciones = 0;
		for(int i = obtenerNumeroVariables(); i<size;i++){
			if(this.tipoId.get(i).equals("fun")){
				numeroFunciones++;
			}
		}
		return numeroFunciones;
	}

	//Busca una funcion con el id especificado y devuelve su tabla de simbolos
	//local
	public TablaSimbolos obtenerTablaLocal(String id){
		int posicionFuncion = obtenerPosicionFuncion(id);
		return this.tablaSimbolosLocal.get(posicionFuncion);
	}

	//Obtiene el arbol local de una funcion en base a su ID
	public Nodo obtenerArbolLocal(String id){
		int posicionFuncion = obtenerPosicionFuncion(id);
		return this.nodoFuncion.get(posicionFuncion);
	}
	
	
	
}



