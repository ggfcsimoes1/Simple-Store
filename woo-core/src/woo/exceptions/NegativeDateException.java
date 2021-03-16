package woo.exceptions;

public class NegativeDateException extends Exception{

    private static final long serialVersionUID = 202003111010L;

    private int _date;
    public NegativeDateException(int date){
        super();
        _date = date;
    }

    /**
   * @return the bad date
   */
    public int getBadDate() {
        return _date;
    }
}
