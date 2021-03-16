package woo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
*  When a store needs stock to sell, it needs to order items. An order has a supplier, 
*  which sends the requested products to be purchased by clients. Orders contain one or 
*  more products, and are automatically paid by the Store to the suppliers. A payment has 
*  an effect on the store's balance.
*/
public class Order extends Transaction implements Serializable{

	private static final long serialVersionUID = 202009192034L;
	private Map<String, Integer> _order = new LinkedHashMap<>(); //products in the order, a linked hashmap is used to preserve insertion order.
	private String _supplierKey;

	/** The constructor used to create a new order.
    *   @param id orders id.
    *   @param supplierKey supplier key.
    *   @param date date of creation and payment.
    */
	Order(int id, String supplierKey, int date){
		super(id, true, 0, date);
		_supplierKey = supplierKey;
	}

	/** A simple setter allowing to modify the supplier's key.
    *   @param key new supplier key (In a String format).
    */
	protected void setSupplierKey(String key){
		_supplierKey = key;
	}

	/** A simple getter allowing acess to the supplier's key.
    *   @return supplier's key (In a String format).
    */
	protected String getSupplierKey(){
		return _supplierKey;
	}

	/** Adds a new product to the order
	*   @param prod new product (Product).
	*   @param amount product amount (int).
	*/
	protected void addProduct(Product prod, int amount){
		double price = getBaseValue();
		_order.put(prod.getKey(), amount);
		setBaseValue(price);
	}

	/** A simple getter allowing acess to a product's amount in the order.
	*   @param key product key.
	*/
	protected int getProductAmount(String key){
		return _order.get(key);
	}

	/** A simple getter allowing acess to the array with all
	*   product keys.
	*   @return Set of Product Keys.
	*/
	protected Set<String> getAllProductKeys(){
		return _order.keySet();
	}

	@Override
	public String toString(){
		String output = getID() + "|" + _supplierKey + "|" + (int) getBaseValue() + "|" + getDateOfPayment();

		for(String key: _order.keySet()){
			output += "\n" + key + "|"  + _order.get(key);
		}
		return output;
	}
}