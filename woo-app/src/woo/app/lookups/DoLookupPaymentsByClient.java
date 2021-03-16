package woo.app.lookups;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
//import other classes

import woo.Transaction;
import woo.exceptions.InvalidClientKeyException;
import woo.app.exceptions.UnknownClientKeyException;

/**
 * Lookup payments by given client.
 */
public class DoLookupPaymentsByClient extends Command<Storefront> {

  private Input<String> _key;

  public DoLookupPaymentsByClient(Storefront storefront) {
    super(Label.PAID_BY_CLIENT, storefront);
    _key = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    try{
      _form.parse();
      for(Transaction t: _receiver.getClientPaidTransactions(_key.value())){
        _display.addLine(t.toString());
      }
      _display.display();
    }catch(InvalidClientKeyException e){
      throw new UnknownClientKeyException(e.getInvalidKey());
    }
  }

}
