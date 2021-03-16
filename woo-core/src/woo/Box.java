package woo;

import java.util.List;

/** 
 *  A Box is a product sold by an agent. It has a relatively small volume, 
 *  accounting for a small number of items, so as to preserve them during shipping. <p>
 *  Boxes have a given service type: <p> <b>
 *  'NORMAL' <p>
 *  'AIR' <p>
 *  'EXPRESS' <p>
 *  'PERSONAL' <p> </b>
 */
public class Box extends Product {

    private enum SType{
        NORMAL,
        AIR,
        EXPRESS,
        PERSONAL,
    }

    private String _serviceType;

    /** User level constructor used to create a box.
    *   @param key box's key.
    *   @param price box's price.
    *   @param critVal box's critic value.
    *   @param supplierKey box's supplierKey..
    *   @param serviceType box's serviceType.
    */
    Box(String key, int price, int critVal, String supplierKey, String serviceType, List<Client> clients) throws IllegalArgumentException{
        super(key, supplierKey, price, critVal, clients);
        _serviceType = serviceType;
        this.setPaymentPeriod(5); //a box has a 5 day payment period
    }

    /** Application level constructor used to create a box from
    *   from a given import file.
    *   @param key box's key.
    *   @param serviceType box's service type.
    *   @param supplierKey box's supplier key.
    *   @param price box's price.
    *   @param critVal box's critic value.
    *   @param stock box's stock.
    */
    Box(String key, String serviceType, String supplierKey, int price, int critVal, int stock, List<Client> clients){
        super(key, supplierKey, price, critVal, stock, clients);
        _serviceType = serviceType;
        this.setPaymentPeriod(5);
    }

    /** A simple getter allowing to acess the service type.
    *   @return the service type (In string form).
    */ 
    protected String getServiceType(){
        return _serviceType;
    }

    /** Method which validates the serviceType attribute.
    *   @return boolean.
    */ 
    protected boolean isValidType(){
        for(SType type: SType.values()){
            if(_serviceType.equals(type.toString())){
                return true;
            }
        }
        return false;
    }

    /** Returns all of the box's information in a string, to be displayed.
    *   @return Box attributes in the desired format.
    */
    @Override
    public String toString(){
        return "BOX|" + super.toString() + "|" + _serviceType;
    }
}
