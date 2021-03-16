package woo;

/**
 * This payment strategy occurs when a costumer pays between the product's period and the deadline. <p> 
 * - If the buyer has a <b>'NORMAL'</b> status, there is no discount to be applied. <p>
 * - If the buyer has a <b>'SELECTION'</b> status, a 5% discount is applied if the payment was 2 days before the deadline. <p>
 * - If the buyer has a <b>'ELITE'</b> status, a 10% discount is always applied. <p>
 */
public class PaymentP2 implements PaymentStrategy {
    public double toPay(Sale sale){
    	String status = sale.getClient().getStatus().toString();
		double price = sale.getPrice();
    	switch(status){
			case "NORMAL":
				//Do nothing...
				break;
				
    		case "SELECTION":
    			if(sale.getDateOfPayment() <= sale.getDeadline() - 2)
    				price -= 0.05 * price;
				break;
				
    		case "ELITE":
				price -= 0.1 * price;
				break;
		}
		return price;
    }
}