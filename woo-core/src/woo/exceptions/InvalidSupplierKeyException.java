package woo.exceptions;

public class InvalidSupplierKeyException extends Exception{

	private static final long serialVersionUID = 202003165011L;

	private String _invKey;

	public InvalidSupplierKeyException(String invKey){
		super();
		_invKey = invKey;
	}

	public String getInvalidKey(){
		return _invKey;
	}
}