package woo.app.suppliers;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
//import other classes
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.InvalidSupplierKeyException;

/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<Storefront> {

  private Input<String> _supplierKey;

  public DoToggleTransactions(Storefront receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    try{
      _form.parse();
      if(_receiver.toggleSupplierTransactions(_supplierKey.value()))
        _display.popup(Message.transactionsOn(_supplierKey.value()));
      else
        _display.popup(Message.transactionsOff(_supplierKey.value()));
    }catch(InvalidSupplierKeyException e){
      throw new UnknownSupplierKeyException(e.getInvalidKey());
    }
  }

}
