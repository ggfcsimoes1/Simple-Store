package woo;

/**
 * This payment strategy occurs when a costumer pays before the deadline and the product's period.
 * Independently of the buyer's status, a 10% discount is always applied. 
 */
public class PaymentP1 implements PaymentStrategy {
    public double toPay(Sale sale){
    	double price = sale.getPrice() - 0.1 * sale.getPrice();
		return price;
	}    
}
