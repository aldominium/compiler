import java.lang.Exception;

@SuppressWarnings("serial")
public class ExistanceException extends Exception{

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	public ExistanceException(){
		super("La variable o funcion no esta declarada");
	}

}