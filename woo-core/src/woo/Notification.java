package woo;

import java.io.Serializable;

/**
 *  When a new product is registered, clients are automatically placed in a notification list,
 *  which may be toggled on/off. Notifications are sent on two conditions: <p> 
 *  - <b> NEW </b>: When the product's stock has increased from 0; <p>
 *  - <b> BARGAIN </b>: When a product's price is now cheaper <p>  
 */
public class Notification implements Serializable{

    private static final long serialVersionUID = 202006110457L;

    private boolean _seen;
    private String _type;
    private Product _product;

    Notification(String type, Product product){
        _type = type;
        _product = product;
        _seen = false;
    }

    /**
     *  Sees a notification.
     *  When a notification is seen, it won't be shown until new discounts/renewal of stock. 
     */
    public void see(){
        _seen = true;
    }

    /**
     *  Checks whether the notification was seen or not.
     */
    public boolean seen(){
        return _seen;
    }


    /**
     *  Formats and returns a notification in a String format.
     */
    @Override
    public String toString(){
        return _type + "|" + _product.getKey() + "|" + _product.getPrice();
    }
    
}
