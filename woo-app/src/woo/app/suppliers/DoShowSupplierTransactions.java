package woo.app.suppliers;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
//import other classes
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.InvalidSupplierKeyException;
import woo.Transaction;
/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<Storefront> {

  private Input<String> _supplierKey;

  public DoShowSupplierTransactions(Storefront receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    try{
      _form.parse();
      for(Transaction t: _receiver.getSupplierTransactions(_supplierKey.value()))
        _display.addLine(t.toString());
      _display.display();
    } catch( InvalidSupplierKeyException e){
      throw new UnknownSupplierKeyException(e.getInvalidKey());
    }
  }
}
