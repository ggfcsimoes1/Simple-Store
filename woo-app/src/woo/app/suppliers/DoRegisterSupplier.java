package woo.app.suppliers;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
//import other classes
import woo.app.exceptions.DuplicateSupplierKeyException;
import woo.exceptions.InvalidSupplierKeyException;

/**
 * Register supplier.
 */
public class DoRegisterSupplier extends Command<Storefront> {

  Input<String> _key, _name, _addr;

  public DoRegisterSupplier(Storefront receiver) {
    super(Label.REGISTER_SUPPLIER, receiver);
    _key = _form.addStringInput(Message.requestSupplierKey());
    _name = _form.addStringInput(Message.requestSupplierName());
    _addr = _form.addStringInput(Message.requestSupplierAddress());
  }

  @Override
  public void execute() throws DialogException {
    try{
    _form.parse();
    _receiver.registerSupplier(_key.value(), _name.value(), _addr.value());
    
    } catch (InvalidSupplierKeyException e){
      throw new DuplicateSupplierKeyException(e.getInvalidKey());
    }
  }
}
