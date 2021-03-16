package woo.app.suppliers;

import pt.tecnico.po.ui.Command;                                                                                                          
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
//import other classes

import woo.Supplier;
import woo.Transaction;
import woo.exceptions.InvalidSupplierKeyException;
/**
 * Show all suppliers.
 */
public class DoShowSuppliers extends Command<Storefront> {

  public DoShowSuppliers(Storefront receiver) {
    super(Label.SHOW_ALL_SUPPLIERS, receiver);
    //FIXME init input fields
  }

  @Override
  public void execute() throws DialogException {
    try{
      for(Supplier supp: _receiver.getAllSuppliers()){
        if (_receiver.getSupplierStatus(supp))
          _display.addLine(supp.toString() + Message.yes());
        else
          _display.addLine(supp.toString() + Message.no());
      }
      
      double tVal = 0;
      double max = 0;
      String suppKey = "";
      for(Supplier supp: _receiver.getAllSuppliers()){
        for(Transaction t: _receiver.getSupplierTransactions(supp.getKey())){
          
          tVal += t.getBaseValue();
        }
        if(tVal > max){
          max = tVal;
          suppKey = supp.getKey();
        }
      }
      _display.addLine("Melhor fornecedor: " + _receiver.getSupplier(suppKey));

      _display.display();
    } catch(InvalidSupplierKeyException e){
      _display.popup("\nNão existem transações registadas, try again!");
    }
  }


}
