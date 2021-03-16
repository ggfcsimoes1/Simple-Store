package woo.exceptions;

public class InvalidClientKeyException extends Exception{

	private static final long serialVersionUID = 202003111011L;

	private String _invKey;

	public InvalidClientKeyException(String invKey){
		super();
		_invKey = invKey;
	}

	public String getInvalidKey(){
		return _invKey;
	}
}