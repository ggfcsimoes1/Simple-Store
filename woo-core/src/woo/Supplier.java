package woo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/** Suppliers deliver orders.
 *  Each supplier has a name and an adress.
 *  Given a supplier, it's possible to get it's transaction history.
 *  It's also possible to activate/deactivate their ability to
 *  deliver orders. An inactive supplier cannot make orders.
 */
public class Supplier implements Serializable, Comparable<Supplier>{

    private static final long serialVersionUID = 202008900457L;

    private String _name;
    private String _addr;
    private boolean _status = true;
    private String _key;
    private ArrayList<Order> _supplierOrders = new ArrayList<>();

    /** Constructor used to create a supplier.
    *   @param key supplier's key.
    *   @param name supplier's name.
    *   @param addr supplier's address.
    */
    Supplier(String key, String name, String addr){
        _key = key;
        _name = name;
        _addr = addr;  
    }

    /** A simple getter allowing to get the supplier's key.
    *   @return supplier's key  (In a String format).
    */ 
    public String getKey(){
        return _key;
    }

    /** A simple getter allowing to get the supplier's activity status.
    *   @return supplier's status  (In a boolean format).
    */
    protected boolean getStatus(){
        return _status;
    }

    /** Toggles the supplier's activity status.
    */
    protected void toggleStatus(){
        _status = !_status;
    }


    /** Associates an order with a certain supplier.
    *   @return void.
    */ 
    protected void addOrder(Order order){
        _supplierOrders.add(order);
    }

    /** A simple getter allowing acess to a supplier's transaction.
    *   @return List of orders.
    */ 
    protected List<Order> getOrders(){
        return Collections.unmodifiableList(_supplierOrders);
    }


    /** A function that compares the keys which allows
    *   alphabetical sorting.
    */ 
    @Override
    public int compareTo(Supplier other) {
        return _key.compareToIgnoreCase(other.getKey());
    }

    /** Returns all of the supplier's information in a string, to be displayed.
    *   @return supplier's attributes in the desired format.
    */
    @Override
    public String toString(){
        return _key + "|" + _name + "|" + _addr + "|";
    }
}
