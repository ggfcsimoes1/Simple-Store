package woo;

import java.io.Serializable;

/*
* A transaction as an identifier (_id) and can be paid (_paid) 
* in the specified date (_dateOfPayment). 
* It also stores the base value of the transaction (_baseValue), preventing future changes
* in the orders products prices.
*/
public abstract class Transaction implements Serializable{

	private static final long serialVersionUID = 202009192024L;
	private int _id;
	private boolean _paid;
	private int _dateOfPayment;
	/*used when a payment is simulated*/
	private int _simulationDate;
	private double _baseValue;

	/** The constructor used to create a new transaction.
    *   @param id transaction id.
    *   @param p true if paid, false otherwise.
    *   @param baseValue base value of the transaction.
    *   @param date date of payment.
    */
	public Transaction(int id, boolean p, double baseValue, int date){
		_id = id;
		_paid = p;
		_baseValue = baseValue;
		_dateOfPayment = date;
	}

	/** Verify if the transaction is paid.
	* @return boolean.
	*/
	protected boolean paid(){
		return _paid;
	}

	/** A simple setter allowing to modify the order's base value.
    *   @param v new base value (double).
    */
	protected void setBaseValue(double v){
		_baseValue = v;
	}

	/** A simple setter allowing to modify the order's date of payment.
    *   @param date new date (int).
    */
	protected void setDateOfPayment(int date){
		_dateOfPayment = date;
	}

	/** A simple setter allowing to modify the orde's date of payment simulation.
    *   @param date new date (int).
    */
	protected void setDateOfSimulation(int date){
		_simulationDate = date;
	}

	/** Sets a transaction as paid.
    *   @param paid boolean.
    */
	protected void setPaid(boolean paid){
		_paid = paid;
	}
	
	/** A simple getter allowing acess to the transaction's ID.
    *   @return transaction's ID (int).
    */ 
	protected int getID(){
		return _id;
	}

	/** A simple getter allowing acess to the transaction's base value.
    *   @return base value (double).
    */ 
	public double getBaseValue(){
		return _baseValue;
	}

	/** A simple getter allowing acess to the transaction's date of payment.
    *   @return date (int).
    */ 
	protected int getDateOfPayment(){
		return _dateOfPayment;
	}

	/** A simple getter allowing acess to the transaction's date of payment simulation.
    *   @return date (int).
    */
	protected int getDateOfSimulation(){
		return _simulationDate;
	}

	@Override
	public String toString(){
		return String.valueOf(_id);
	}
}

