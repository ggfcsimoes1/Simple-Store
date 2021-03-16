package woo;

/**
 * This payment strategy occurs when a costumer pays between the deadline and the product's period. <p> 
 * - If the buyer has a <b>'NORMAL'</b> status, there is a 5% fine to be applied. <p>
 * - If the buyer has a <b>'SELECTION'</b> status, a 2% daily fine is applied if the payment was 1 day after the deadline. <p>
 * - If the buyer has a <b>'ELITE'</b> status, no fine is applied. <p>
 */
public class PaymentP3 implements PaymentStrategy {
    public double toPay(Sale sale){
    	String status = sale.getClient().getStatus().toString();
    	double price = sale.getPrice();
		sale.setLatePayment();
		int delay = sale.getDateOfPayment() - sale.getDeadline(); 

    	switch(status){
			case "NORMAL":
				price += 0.05 * delay *  price;
				break;
			case "SELECTION": 
				if (delay > 1)
					price += 0.02 * delay *  price;
				break;
    		case "ELITE":
				price -= 0.05 * price;
				break;
    	}
    	return price;
    }
}