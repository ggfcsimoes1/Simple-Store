package woo.app.transactions;

import pt.tecnico.po.ui.Command;              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
//import other classes
import java.util.Map;
import java.util.LinkedHashMap;
import woo.app.exceptions.UnauthorizedSupplierException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.WrongSupplierException;

import woo.exceptions.InvalidProductKeyException;
import woo.exceptions.InvalidProductSupplierException;
import woo.exceptions.InvalidSupplierKeyException;
import woo.exceptions.InvalidSupplierStatusException;

/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<Storefront> {

  private Input<String> _supplierKey, _productKey;
  private Input<Boolean> _request;
  private Input<Integer> _amount;
  
  public DoRegisterOrderTransaction(Storefront receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver); 
  }

  @Override
  public final void execute() throws DialogException {
    try{
      LinkedHashMap<String, Integer> products = new LinkedHashMap<>();
      _supplierKey = _form.addStringInput(Message.requestSupplierKey());
      _productKey = _form.addStringInput(Message.requestProductKey());
      _amount = _form.addIntegerInput(Message.requestAmount());
      _request = _form.addBooleanInput(Message.requestMore());
      _form.parse();
      products.put(_productKey.value(), _amount.value());
      _form.clear(); 
      while(_request.value()) {
        _productKey = _form.addStringInput(Message.requestProductKey());
        _amount = _form.addIntegerInput(Message.requestAmount());
        _request = _form.addBooleanInput(Message.requestMore());
        _form.parse();
        products.put(_productKey.value(), _amount.value());
        _form.clear();
      } 
      
      _receiver.registerOrder(_supplierKey.value(), products);
       
    } catch(InvalidSupplierKeyException e) {
    	throw new UnknownSupplierKeyException(e.getInvalidKey());
    } catch(InvalidProductSupplierException e) {
    	throw new WrongSupplierException(e.getInvalidSupplierKey(), e.getInvalidProductKey());
    } catch(InvalidSupplierStatusException e) {
    	throw new UnauthorizedSupplierException(e.getInvalidKey());
    } catch(InvalidProductKeyException e) {
    	throw new UnknownProductKeyException(e.getInvalidKey());
    } 
  }

}
