package woo.exceptions;

public class InvalidTransactionKeyException extends Exception{

	private static final long serialVersionUID = 312003165011L;

	private int _invKey;

	public InvalidTransactionKeyException(int invKey){
		super();
		_invKey = invKey;
	}

	public int getInvalidKey(){
		return _invKey;
	}
}