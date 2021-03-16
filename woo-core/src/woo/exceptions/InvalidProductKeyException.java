package woo.exceptions;

public class InvalidProductKeyException extends Exception{

	private static final long serialVersionUID = 202003111045L;

	private String _invKey;

	public InvalidProductKeyException(String invKey){
		super();
		_invKey = invKey;
	}

	public String getInvalidKey(){
		return _invKey;
	}
}