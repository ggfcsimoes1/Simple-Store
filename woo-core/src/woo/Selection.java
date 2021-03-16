package woo;

/** The Selection status is an intermediate status acquired by the client. If a client has this status: <p>
 *  -Making a payment before the deadline and the specific product period awards the client with a <b>10% discount</b>; <p>
 *  -Making a payment between the specific product period and the deadline awards the client with a <b>5% discount if the payment 
 *   is made up to two days before the deadline</b> or <b>no discount if the payment is made two days or less before the deadline</b>; <p>
 *  -Making a payment between the specific product period and the deadline fines the client with a <b>no increase if the payment is late by 
 *   one day</b> or <b>2% increase per day if the payment is made after that</b> (demotion occurs); <p>
 *  -Making a payment after the deadline and the specific product period fines the client with a <b>5% increase per day</b> (demotion occurs); <p>
 */ 
public class Selection extends Status.State {

    Selection(Status status){
        status.super();
    }

    /**
     *  Promotes a client.
     */
    @Override
    public void promotion(){
        setState(new Elite(getStatus()));
    }

    /** 
    *   Demotes the client. 
    */ 
    @Override
    public void demotion(){
        setState(new Normal(getStatus())); 
    }

    /**  
    *   Returns the client status in a string format.
    */ 
    public String status(){
        return "SELECTION";
    }  
}
