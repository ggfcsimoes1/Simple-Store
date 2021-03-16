package woo.exceptions;

public class InvalidServiceTypeException extends Exception{

	private static final long serialVersionUID = 202233155011L;

	private String _invST;

	public InvalidServiceTypeException(String invST){
		super();
		_invST = invST;
	}

	public String getInvalidType(){
		return _invST;
	}
}