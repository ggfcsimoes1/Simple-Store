package woo;

/** 
 *  The Elite status is the highest status a client can acquire. If a client has this status: <p>
 *  -Making a payment before the deadline awards the client with a <b>10% discount</b>; <p>
 *  -Making a payment between the deadline and the specific product period awards the client with a <b>5% discount</b> (demotion still occurs); <p>
 *  -Making a payment after the deadline and the specific product period awards the client <b>no discount</b> (demotion still occurs); <p>
 */ 
public class Elite extends Status.State {

    Elite(Status status){
        status.super();
    }

    //promotion behaviour doesn't need to be redefined because it's follows the default one (set in the superclass).

    /** 
    *   Demotes the client. 
    */ 
    @Override
    public void demotion(){
        setState(new Selection(getStatus())); 
    }

    /**  
    *   Returns the client status in a string format.
    */ 
    public String status(){
        return "ELITE";
    }  
}
