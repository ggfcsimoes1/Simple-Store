package woo.exceptions;

public class InvalidProductSupplierException extends Exception{

	private static final long serialVersionUID = 982003165011L;

    private String _sKey;
    private String _pKey;

	public InvalidProductSupplierException(String sKey, String pKey){
		super();
        _sKey = sKey;
        _pKey = pKey;
	}

	public String getInvalidSupplierKey(){
		return _sKey;
	}

	public String getInvalidProductKey(){
		return _pKey;
	}
}