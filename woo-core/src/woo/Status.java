package woo;

import java.io.Serializable;

/** Clients that interact with a store acquire a status. There are three status a client can have: <p><b>
 *  'NORMAL' <p>
 *  'SELECTION' <p>
 *  'ELITE' <p> </b>
 *  These statuses affect the interaction between these two parties: A client with the 'NORMAL' status
 *  will have less benefits than a client with the 'ELITE' status. Clients that make purchases gain points 
 *  that are accounted, awarding them with a promotion after reaching certain thresholds: <p>
 *  <b>'NORMAL' -> 'SELECTION'</b>: Awarded after acquiring 2000+ points. <p>
 *  <b>'SELECTION' -> 'ELITE'</b>: Awarded after acquiring 25000+ points. <p>
 *  If a payment occurs with delay, a demotion occurs. These demotions may vary according to the deadline: <p>
 *  <b>'ELITE' -> 'SELECTION'</b>: Occurs after a payment has a delay of 15+ days. <p>
 *  <b>'SELECTION' -> 'NORMAL'</b>: Occurs after a payment has a delay of 2+ days. <p>
**/
public class Status implements Serializable{

    private static final long serialVersionUID = 202005110458L;
    
    private State _state;

    public abstract class State implements Serializable{

        private static final long serialVersionUID = 202006110458L;
        
        public void promotion(){
            //Default: no nothing (when already at the top-most level)
        }

        public void demotion(){
            //Default: no nothing (when already at the bottom-most level)
        }

        public abstract String status();

        /**
         * Sets the client's status with a new given state
         * @param newState the new status.
         */
        protected void setState(State newState){
            _state = newState;
        }

        /**
         * Returns the current client's status.
         * @param Status the status of a given client.
         */
        protected Status getStatus(){
            return Status.this;
        }
    }

    /**
     * Default status initializer.
     * Creates a 'NORMAL' status (initial client status).
     */ 
    public Status(){
        _state = new Normal(this);
    }

    /**
     * Promotes a client.
     */ 
    public void promotion() {
        _state.promotion();
    }

    /**
     * Demotes a client.
     */ 
    public void demotion() {
        _state.demotion();
    }

    /**
     * Returns the current client's status.
     * @param Status the status of a given client (string).
     */
    public String status() {
        return _state.status();
    }

    @Override
    public String toString(){
        return _state.status();
    }

}
