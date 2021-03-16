package woo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/** 
 *  Clients purchase items whose payment can be made later on. <p>
 *  Each client has a name (String) and an address (String). 
 *  Associated to each client there is still information 
 *  regarding the purchases that were made.
 *  Each client has also an associated status: <p> <b>
 *      NORMAL <p>
 *      SELECTION <p>
 *      ELITE <p></b>
 *  Given a client, there is also the possibility of getting its' purchase history.
 */
public class Client implements Serializable, Comparable<Client>, Observer{

    private static final long serialVersionUID = 202007110457L;

    private String _key = "";
    private String _name = "";
    private String _addr = "";
    private Status _status;
    private double _points = 0;
    private double _purchasesValue = 0; 
    private double _paidPurchasesValue = 0;
    private ArrayList<Sale> _clientPurchases = new ArrayList<>();
    private ArrayList<Notification> _notifications = new ArrayList<>();
    private List<Product> _products = new ArrayList<>();
    

    /** The constructor used to create a new client.
    *   @param key client key.
    *   @param name client name.
    *   @param addr client's address.
    *   @param products the list of products to observe.
    */
    Client(String key, String name, String addr, List<Product> products){
        _key = key;
        _name = name;
        _addr = addr;
        _status = new Status();
        _products = products;
        for(Subject product: products) 
            product.registerObserver(this); //this client is now an observer of the given products
    }

    /** Updates the notifications.
    *   @param product the product to update.
    *   @param type the type of notification ot be sent.
    */ 
    @Override
    public void update(Product product, String type){
        _notifications.add(new Notification(type, product));
    }

    /** Increases the client's value of paid purchases.
    *   @param addr the price to increment the attribute (double).
    */   
    protected void incrementPaidPurchasesValue(double delta){
        _paidPurchasesValue += delta;
    }

    /** Increases the 'points' attribute
    *   @param delta the increase in value (double).
    */ 
    protected void increasePoints(double delta){
        _points += delta;
    }

    /** Decreases the 'points' attribute
    *   @param delta the decrease in value (double).
    */ 
    protected void decreasePoints(double delta){
        _points -= delta;
    }

    /** A simple getter allowing acess to the client's key.
    *   @return client's key (In a String format).
    */ 
    protected String getKey(){
        return _key;
    }

    protected double getPaidPurchasesValue(){
        return _paidPurchasesValue;
    }

    /** A simple getter allowing acess to the client's name.
    *   @return name (In a String format).
    */ 
    protected String getName(){
        return _name;
    }

    /** A simple getter allowing acess to the client's adress.
    *   @return adress (In a String format).
    */ 
    protected String getAdress(){
        return _addr;
    }

    /** A simple getter allowing acess to the client's points.
    *   @return points (double).
    */ 
    protected double getPoints(){
        return _points;
    }

    /** A simple getter allowing acess to a client's purchase with a given key.
    *   @return client purchase (Sale).
    */ 
    protected Sale getPurchase(int saleKey){
        return _clientPurchases.get(saleKey);
    }

    /** A simple getter allowing acess the client's status.
    *   @return client status (Status).
    */ 
    protected Status getStatus(){
        return _status;
    }

    /** A method that calculates the status of a client after a given sale.
    *   @param sale the sale used to perform the estimate.
    */ 
    protected void estimateStatus(Sale sale){
        
        if(sale.getDateOfPayment() - sale.getDeadline() > 15 && 
            _status.toString().equals("ELITE")){
            _status.demotion();
            _points -= 0.75 * _points;
        }
        else if(sale.getDateOfPayment() - sale.getDeadline() > 2 && 
            _status.toString().equals("SELECTION")){
            _status.demotion();
            _points -= 0.9 * _points;
        } 
        else {
            if(_points > 2000 && _status.toString().equals("NORMAL")){
                _status.promotion();
            } 
            if (_points > 25000 && _status.toString().equals("SELECTION")){
                _status.promotion();
            }
        }
    }

    /** Associates a sale with a certain client.
    *   @return void.
    */ 
    protected void addPurchase(Sale sale){
        _clientPurchases.add(sale);
        _purchasesValue += sale.getPrice();
    }

    /** A simple getter allowing acess to a client's transactions.
    *   @return List of sales.
    */ 
    protected List<Sale> getPurchases(){
        return Collections.unmodifiableList(_clientPurchases);
    }

    /** A simple getter allowing acess to a client's paid transactions.
    *   @return List of sales.
    */ 
    protected List<Sale> getPaidPurchases(){
        ArrayList<Sale> lst = new ArrayList<>();
        for(Sale s: _clientPurchases)
            if(s.paid())
                lst.add(s);

        return Collections.unmodifiableList(lst);
    }

    /** A simple getter allowing acess to a client's notifications.
    *   @return List of notifications.
    */ 
    protected List<Notification> getNotifications(){
        return Collections.unmodifiableList(_notifications);
    }

    /** A function that compares the keys which allows
    *   alphabetical sorting.
    */ 
    @Override
    public int compareTo(Client other) {
        return _key.compareToIgnoreCase(other.getKey());
    }

    /** Returns all of the client's information in a string, to be displayed.
    *   @return client's attributes in the desired format.
    */
    @Override
    public String toString(){
        return _key + "|" + _name + "|" + _addr + "|" + _status.toString() + "|" + (int) _purchasesValue + "|" + (int) _paidPurchasesValue;
    }

}
