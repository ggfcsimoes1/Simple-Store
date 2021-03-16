package woo.app.transactions;

import pt.tecnico.po.ui.Command;                                                                                                           
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
// import other classes
import woo.app.exceptions.UnknownTransactionKeyException;
import woo.exceptions.InvalidTransactionKeyException;

/**
 * Pay transaction (sale).
 */
public class DoPay extends Command<Storefront> {

  Input<Integer> _salesKey;
  
  public DoPay(Storefront storefront) {
    super(Label.PAY, storefront);
    _salesKey = _form.addIntegerInput(Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException {
  	try{
  		_form.parse();
  		_receiver.pay(_salesKey.value());

  	} catch (InvalidTransactionKeyException e) {
  		throw new UnknownTransactionKeyException(e.getInvalidKey());
  	}
  }

}
