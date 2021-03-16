package woo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 *  Clients purchase products supplied from the stores. These products 
 *  have a supplier, a price, a critic value and stock. When a new product
 *  is registered, clients are automatically placed on a notification list,
 *  which will notify them when the stock increases ('NEW'), or the price 
 *  decreases ('BARGAIN').
 */
public class Product implements Serializable, Comparable<Product>, Subject{

    private static final long serialVersionUID = 202007110127L;
    
    private String _key;
    private String _supplierKey;
    private int _price;
    private int _stock;
    private int _critVal;
    private int _paymentPeriod;
    private ArrayList<Observer> _observers = new ArrayList<>();

    /** User level constructor used to create a product.
    *   @param key product key.
    *   @param supplierKey supplier key.
    *   @param price product's price.
    *   @param critVal product's critical value.
    */
    Product(String key, String supplierKey, int price, int critVal, List<Client> clients){
        _key = key;
        _supplierKey = supplierKey;
        _price = price;
        _critVal = critVal;
        _stock = 0;
        for(Client client: clients)
            this.registerObserver(client);
    }

    /** Application level constructor used to create a product.
    *   @param key product key.
    *   @param supplierKey supplier key.
    *   @param price product's price.
    *   @param critVal product's critical value.
    *   @param stock product's stock.
    */
    Product(String key, String supplierKey, int price, int critVal, int stock, List<Client> clients){
        _key = key;
        _supplierKey = supplierKey;
        _price = price;
        _critVal = critVal;
        _stock = stock;
        for(Client client: clients)
            this.registerObserver(client);
    }

    /** A simple setter allowing to get the product's key.
    *   @param key the new key (In a String format).
    */   
    protected void setKey(String key){
        _key = key;
    }

    /** A simple setter allowing to set the product's supplier key.
    *   @param supplierKey the new key (In a String format).
    */   
    protected void setSupplierKey(String supplierKey){
        _supplierKey = supplierKey;
    }

    /** A simple setter allowing to set the product's price.
    *   @param price the new price (int).
    */  
    protected void setPrice(int price){
        setProductPrice(price);
        _price = price;
    }

    /** A simple setter allowing to set the product's critic value.
    *   @param critVal the new critic value (int).
    */ 
    protected void setCritVal(int critVal){
        _critVal = critVal;
    }

    /** A simple setter allowing to set the product's stock.
    *   @param stock the new stock value (int).
    */
    protected void setStock(int stock){
        setProductStock(stock);
        _stock = stock;
    }

    /** A simple setter allowing to set the product's payment period.
    *   @param paymentPeriod the new payment period (int).
    */
    protected void setPaymentPeriod(int paymentPeriod){
        _paymentPeriod = paymentPeriod;
    }

    /** A simple getter allowing to get the product's key.
    *   @return Product's key (String).
    */
    protected String getKey(){
        return _key;
    }

    /** A simple getter allowing to get the product's supplier key.
    *   @return Product's supplier key (String).
    */
    protected String getSupplierKey(){
        return _supplierKey;
    }

    /** A simple getter allowing to get the product's price.
    *   @return Product's price (int).
    */
    protected int getPrice(){
        return _price;
    }

    /** A simple getter allowing to get the product's critic value.
    *   @return Product's critic value (int).
    */
    protected int getCritVal(){
        return _critVal;
    }

    /** A simple getter allowing to get the product's stock.
    *   @return Product's stock (int).
    */
    protected int getStock(){
        return _stock;
    }

    /** A simple getter allowing to get the product's payment period.
    *   @return payment period (int).
    */
    public int getPaymentPeriod(){
        return _paymentPeriod;
    }

    /** Verify if a client is observing the product.
    *   @param c Client to verify.
    *   @return boolean.
    */
    public boolean isObserver(Client c){
        return _observers.contains(c);
    }

    /** Add a new observer to the product.
    *   @param o new observer (Observer).
    */
    public void registerObserver(Observer o){
        _observers.add(o);
    }

    /** Remove an observer from the product.
    *   @param o removed observer (Observer).
    */
    public void removeObserver(Observer o){
        int i = _observers.indexOf(o);
        _observers.remove(i);
    }

    /** Notify all Obeservers with a notification of the
    *   given type depending on the type of occurrence.
    *   @param type type of notification (String).
    */
    public void notifyObservers(String type){
        for(Observer observer: _observers){
            observer.update(this, type);
        }
    }

    /** Notify all observers regarding a change in
    * the product's stock.
    */
    protected void productChanged(){
        notifyObservers("NEW");
    }

    /** Notify all Observers regarding a descrease in
    * the product's price.
    */     
    protected void productValueChanged() { 
        notifyObservers("BARGAIN"); 
    }

    /** A simple setter allowing to set the product's price.
    *   @param price the new price (int).
    */    
    protected void setProductPrice(int price) {
        if (price < _price)
            productValueChanged();
        _price = price;    
    }

    /** A simple setter allowing to set the product's stock.
    *   @param stock the new stock (int).
    */
    protected void setProductStock(int stock) {
        if (stock > _stock)
            productValueChanged();
        _stock = stock;
    }

    /** A function that compares the keys which allows
    *   alphabetical sorting.
    */ 
    @Override
    public int compareTo(Product other) {
        return _key.compareTo(other.getKey());
    }

    /** Returns all of the product's information in a string, to be displayed.
    *   @return Product's attributes in the desired format.
    */
    @Override
    public String toString(){
        return  _key + "|" + _supplierKey + "|" + _price + "|" + _critVal + "|" + _stock; 
    }
}

