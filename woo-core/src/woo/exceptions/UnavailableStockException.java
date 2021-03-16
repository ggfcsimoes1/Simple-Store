package woo.exceptions;

public class UnavailableStockException extends Exception{

    private static final long serialVersionUID = 191803165011L;
    
    private String _invKey;
    private int _reqStock;
    private int _avStock;

	public UnavailableStockException(String invKey, int reqStock, int avStock){
        super();
        _invKey = invKey;
		_reqStock = reqStock;
        _avStock = avStock;
	}

    public String getInvalidKey(){
        return _invKey;
    }

	public int getInvalidReqStock(){
		return _reqStock;
    }
    
    public int getInvalidAvStock(){
		return _avStock;
	}
}