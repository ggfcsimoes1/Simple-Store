package woo;

import java.io.Serializable;

/**
 * A store sells a product to a client. Each sale has a deadline. 
 * When registered, a sale is not considered as paid. The price to
 * pay is dependent on the date of payment, where discounts/fines
 * may be applied.
 */
public class Sale extends Transaction implements Serializable{

	private static final long serialVersionUID = 202009192034L;
	private Client _client;
	private Product _product;
	private int _amount;
	private int _deadline;
	private double _paidValue = 0;
	private boolean _late = false;
	
    /** 
	 *  User level constructor used to create a sale.
     *  @param id sale unique ID.
     *  @param client client key.
     *  @param deadline product's deadline.
	 *  @param product product to sell.
	 *  @param amount product's amount.
     */
	Sale(int id, Client client, int deadline, Product product, int amount){
		super(id, false, amount * product.getPrice(), 0);
		_client = client;
		_product = product;
		_amount = amount;
		_deadline = deadline; 
	}

	/**
	 * If a payment was late, set it as such.
	 */
	protected void setLatePayment(){
		_late = true;
	}

	/**
	 * Returns the client of a given sale.
	 * @return client with the given key. 
	 */
	protected Client getClient(){
		return _client;
	}

	/**
	 * Returns the product key of a given sale.
	 * @return product key. (String) 
	 */
	protected String getProductKey(){
		return _product.getKey();
	}

	/**
	 * Returns the client key of a given sale.
	 * @return client key. (String) 
	 */
	protected String getClientKey(){
		return _client.getKey();
	}

	/**
	 * Returns the product sold on a given sale.
	 * @return product.
	 */
	protected Product getProduct(){
		return _product;
	}

	/**
	 * Returns the amount of a product sold on a given sale.
	 * @return amount. (int)
	 */
	protected int getAmount(){
		return _amount;
	}

	/**
	 * Returns the deadline of a product sale.
	 * @return deadline. (int)
	 */
	protected int getDeadline(){
		return _deadline;
	}

	/**
	 * Returns the price of a sale.
	 * @return price. (int)
	 */
	protected double getPrice(){
		return getBaseValue();
	}

	/**
	 * Sets the price of a sale.
	 * @param value new value.
	 */
	protected void setPaidPrice(double value){
		_paidValue = value;
	}

	/**
	 * Returns the paid price of a sale.
	 * @return paid price. (double)
	 */
	public double getPaidPrice(){
		if(!paid()){
			return estimatePaymentValue(getDateOfSimulation());
		}
		return _paidValue;
		
	}

	/**
	 * Estimates the price to pay of a transaction
	 * according to the date given.
	 * @param date current date.
	 * @return paid price. (double)
	 */
	protected double estimatePaymentValue(int date){
		double toPay = getBaseValue();
		setDateOfPayment(date);
	
		if(getDeadline() - date >= _product.getPaymentPeriod()){
			
			PaymentStrategy P1 = new PaymentP1();
			toPay = P1.toPay(this);

		} else if((getDeadline() - date >= 0) && (getDeadline() - date < _product.getPaymentPeriod())) {
			
			PaymentStrategy P2 = new PaymentP2();
			toPay = P2.toPay(this);

		} else if((date - getDeadline() > 0) && (date - getDeadline() <= _product.getPaymentPeriod())) {
			PaymentStrategy P3 = new PaymentP3();
			toPay = P3.toPay(this);

		} else if(date - getDeadline() > _product.getPaymentPeriod()){
			PaymentStrategy P4 = new PaymentP4();
			toPay = P4.toPay(this);
		}
		return toPay;
	}

	/**
	 * Estimates the points earned in a transaction.
	 * @param date current date.
	 * @param paid price. (double)
	 * @return purchase points.
	 */
	protected double estimatePurchasePoints(int date, double paidPrice){
		if(!_late)
			return 10 * paidPrice;
		return 0;
	}

	/**
	 * Estimates the points earned in a transaction.
	 * @param paidPrice paid price.
	 * @param awardedPoints points earned.
	 * @param date payment date.
	 */
	public void pay(double paidPrice, double awardedPoints, int date){
		setPaid(true);
		setPaidPrice(paidPrice);
		getClient().incrementPaidPurchasesValue(paidPrice);
		getClient().increasePoints(awardedPoints);
		getClient().estimateStatus(this);
	}

	@Override
	public String toString(){
		
		if(paid()){
			return super.toString() + "|" + getClientKey() + "|" + getProductKey() + "|" + getAmount() + "|" +
			(int) getBaseValue() + "|" + (int) getPaidPrice() +"|" + getDeadline() + "|" + getDateOfPayment();
		}

		return super.toString() + "|" + getClientKey() + "|" + getProductKey() + "|" + getAmount() + "|" +
			(int) getBaseValue() + "|" + (int) getPaidPrice() +"|" + getDeadline();
	}
}
