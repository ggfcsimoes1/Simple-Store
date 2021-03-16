package woo;

import java.util.List;

/** 
 *  Containers are products whose volume is much larger than boxes, 
 *  allowing to store a larger amount of items. 
 *  Containers have the same service type as boxes. They also have
 *  four service quality levels: <p><b>
 *      B4 <p>
 *      C4 <p>
 *      C5 <p>
 *      DL <p> </b>
 */ 
public class Container extends Box {

    private enum SLevel{
        B4,
        C4,
        C5,
        DL;
    }
    
    private String _serviceLevel;
    
    /** User level constructor used to create a container.
    *   @param key box's key.
    *   @param price box's price.
    *   @param critVal box's critic value.
    *   @param supplierKey box's supplierKey.
    *   @param serviceType box's serviceType.
    *   @param serviceLevel box's serviceLevel.
    */
    Container(String key, int price, int critVal, String supplierKey, String serviceType, String serviceLevel, List<Client> clients){
        super(key, price, critVal, supplierKey, serviceType, clients);
        _serviceLevel = serviceLevel;
        this.setPaymentPeriod(8); //a container has a payment period of 8 days.
    }
     
    /** Application level constructor used to create a container.
    *   @param key box's key.
    *   @param serviceType box's serviceType.
    *   @param serviceLevel box's serviceLevel.
    *   @param supplierKey box's supplierKey.
    *   @param price box's price.
    *   @param critVal box's critic value.
    *   @param stock box's stock.
    */
    Container(String key, String serviceType, String serviceLevel, String supplierKey, int price, int critVal, int stock, List<Client> clients){
        super(key, serviceType ,supplierKey, price, critVal, stock, clients);
        _serviceLevel = serviceLevel;
        this.setPaymentPeriod(8);
    }

    /** Method which validates the serviceLevel attribute.
    *   @return boolean.
    */ 
    protected boolean isValidLevel(){
        for(SLevel level: SLevel.values()){
            if(_serviceLevel.equals(level.toString())){
                return true;
            }
        }
        return false;
    }

    /** A simple getter allowing to acess the service level.
    *   @return the service type (In string form).
    */ 
    protected String getServiceLevel(){
        return _serviceLevel;
    }

    /** Returns all of the containers's information in a string, to be displayed.
    *   @return Container's attributes in the desired format.
    */
    @Override
    public String toString(){
        return "CONTAINER|" + getKey() + "|" + getSupplierKey() + "|" + getPrice() + "|" + getCritVal() + "|" + getStock() + "|" + getServiceType() + "|" + getServiceLevel();
    }
}
