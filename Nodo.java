
public class Nodo {
	
	public final String PYC = ";";
	public final String EQUALS = "=";
	public final String ASIG = ":=";
	public final String PRINT = "PRINT";
	public final String READ = "READ";
	public final String NUM = "NUM";
	public final String ID = "ID";
	public final String LESS = "<";
	public final String GREATHER = ">";
	public final String IF = "IF";
	
	
	
	private Nodo leftChild;
	private Nodo rightChild;
	private String kind;
	private double valor;
	private String id;

	
	public Nodo(){
		this.leftChild = null;
		this.kind = null;
		this.rightChild = null;
		this.valor = 0;
		this.id = null;
	}
	public Nodo(String kind,double valor){
		this.leftChild = null;
		this.kind = kind;
		this.rightChild = null;
		this.valor = valor;
		this.id = null;
	}

	public Nodo(String kind,Nodo izq){
		this.leftChild = izq;
		this.kind = kind;
		this.rightChild = null;
		this.valor = valor;
		this.id = null;
	}

	public Nodo(String kind,String id){
		this.leftChild = null;
		this.kind = kind;
		this.rightChild = null;
		this.valor = -1;
		this.id = id;
	}
	
	public Nodo(Nodo leftChild,String kind,Nodo rightChild,String id,double valor){
		this.leftChild = leftChild;
		this.kind = kind;
		this.rightChild = rightChild;
		this.valor = valor;
		this.id = id;
	}
	
	public Nodo(Nodo leftChild,String kind,Nodo rightChild){
		this.leftChild = leftChild;
		this.kind = kind;
		this.rightChild = rightChild;
		this.valor = -1;
		this.id = null;
	}


	
	public Nodo(Nodo leftChild,String kind,Nodo rightChild,double valor){
		this.leftChild = leftChild;
		this.kind = kind;
		this.rightChild = rightChild;
		this.valor = valor;
		this.id = null;
	}
	
	
	public boolean isInstruction(){
		if(this.kind.equals(this.PYC)){
			return true;
		}else{
			return false;
		}
	}
	//revisar
	public boolean isComparation(){
		if(this.kind.equals(this.IF)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isAsignation(){
		if(this.kind.equals(this.ASIG)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isPrint(){
		if(this.kind.equals(this.PRINT)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isNum(){
		if(this.kind.equals(this.NUM)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isID(){
		if(this.kind.equals(this.ID)){
			return true;
		}else{
			return false;
		}
	}

	public boolean isLast(){
		if (this.rightChild == null) {
			return true;
		}else{
			return false;
		}
	}
	
	
	
	public boolean hasChildren(){
		if(this.leftChild != null || this.rightChild != null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean hasRightChild(){
		
		if(this.rightChild != null){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean hasLeftChild(){
			
			if(this.leftChild != null){
				return true;
			}else{
				return false;
			}
			
		}

	public void setLeftChild(Nodo nodo){
		this.leftChild = nodo;
	}

	public void setRightChild(Nodo nodo){
		this.rightChild = nodo;
	}

	public void setKind(String kind){
		this.kind = kind;
	}

	public void setValor(double valor){
		this.valor = valor;
	}

	public void setID(String id){
		this.id = id;
	}
	
	public double getValor(){
		return this.valor;
	}

	public String getKind(){
		return this.kind;
	}
	
	
	
	public Nodo getLeftChild(){
		if(this.hasLeftChild())
			return this.leftChild;
		else{
			return null;
		}
	}
	
	public Nodo getRightChild(){
		if(this.hasRightChild())
			return this.rightChild;
		else{
			return null;
		}
	}
	
	
	public String getID(){
		return this.id;
	}

	public void imprimir(){

		if(this.leftChild != null)
			System.out.println("Left child kind: "+this.leftChild.getKind());
		else{
			System.out.println("Left child kind: null");
		}

		if(this.getKind() != null)
			System.out.println("My kind:"+this.getKind());
		else
			System.out.println("My kind is null");


		if(this.rightChild == null)
			
			System.out.println("Right child kind: null");
		else{
			System.out.println("Right child kind: "+this.rightChild.getKind());
		}

		System.out.println("My valor:"+this.valor);

		if(this.id != null)
			System.out.println("My id:"+this.id);
		else
			System.out.println("My id is null");

	}

}









