package woo;

/**
 * To allow for the addition of other payment methods, the strategy design pattern was used.
 * When a client makes the payment of a purchase, depending on certain conditions, the price 
 * may vary. For this reason there is a need to define several payment methods that depend on
 * the date of payment.
 */
public interface PaymentStrategy {
    public double toPay(Sale sale);  

}
