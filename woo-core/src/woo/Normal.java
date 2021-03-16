package woo;

/** 
 *  The Normal status is the base status a client acquires. If a client has this status: <p>
 *  - Making a payment before the deadline and the specific product period awards the client with a <b>10% discount</b>; <p>
 *  - Making a payment between the specific product period and the deadline awards the client with a <b>no discount</b>; <p>
 *  - Making a payment between the specific product period and the deadline fines the client with a <b>5% increase per day</b> (demotion occurs); <p>
 *  - Making a payment after the deadline and the specific product period fines the client with a <b>10% increase per day</b> (demotion occurs); <p>
 */ 
public class Normal extends Status.State{

    Normal(Status status){
        status.super();
    }

    /**
     *  Promotes a client.
     */
    @Override
    public void promotion(){
        setState(new Selection(getStatus()));
    }

    //default demotion behaviour

    /**  
    *   Returns the client status in a string format.
    */ 
    public String status(){
        return "NORMAL";
    }
}
