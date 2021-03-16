package woo;

/**
 * This payment strategy occurs when a costumer pays after the deadline and the product's period. <p> 
 * - If the buyer has a <b>'NORMAL'</b> status, there is a 10% daily fine to be applied. <p>
 * - If the buyer has a <b>'SELECTION'</b> status, a 5% daily fine to be applied. <p>
 * - If the buyer has a <b>'ELITE'</b> status, no fine is applied. <p>
 */
public class PaymentP4 implements PaymentStrategy {
    public double toPay(Sale sale){
    	String status = sale.getClient().getStatus().toString();
    	double price = sale.getPrice();
		sale.setLatePayment();
		int delay = sale.getDateOfPayment() - sale.getDeadline(); 
    	switch(status){
    		case "NORMAL":
				price += 0.1 * delay *price;
				break;
    		case "SELECTION":
				price += 0.05 * delay * price;
				break;
    		case "ELITE":
				//Does nothing
				break;
    	}
		return price;
    }
}