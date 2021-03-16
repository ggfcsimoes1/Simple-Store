package woo.exceptions;

public class InvalidServiceLevelException extends Exception{

	private static final long serialVersionUID = 202013111011L;

	private String _invSL;

	public InvalidServiceLevelException(String invSL){
		super();
		_invSL = invSL;
	}

	public String getInvalidLevel(){
		return _invSL;
	}
}