package woo.app.products;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
//import other classes
import woo.app.exceptions.UnknownServiceTypeException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownServiceLevelException;

import woo.exceptions.InvalidServiceTypeException;
import woo.exceptions.InvalidSupplierKeyException;
import woo.exceptions.InvalidProductKeyException;
import woo.exceptions.InvalidServiceLevelException;

/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<Storefront> {

  private Input<String> _key, _supplierKey, _SType, _SLevel;
  private Input<Integer> _price, _critVal;

  public DoRegisterProductContainer(Storefront receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    _key = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
    _critVal = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _SType = _form.addStringInput(Message.requestServiceType());
    _SLevel = _form.addStringInput(Message.requestServiceLevel());
    
  }

  @Override
  public final void execute() throws DialogException {
    try{
    	_form.parse();
      	_receiver.registerContainer(_key.value(), _price.value(), _critVal.value(), _supplierKey.value(), _SType.value(), _SLevel.value());
    } catch (InvalidServiceLevelException e){
    	throw new UnknownServiceLevelException(e.getInvalidLevel());
    } catch (InvalidServiceTypeException e){
    	throw new UnknownServiceTypeException(e.getInvalidType());
    } catch (InvalidSupplierKeyException e){
    	throw new UnknownSupplierKeyException(e.getInvalidKey());
    } catch(InvalidProductKeyException e){
      throw new DuplicateProductKeyException(e.getInvalidKey());
    }
  }
}
