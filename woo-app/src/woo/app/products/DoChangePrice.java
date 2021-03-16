package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
//import other classes
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.InvalidProductKeyException;

/**
 * Change product price.
 */
public class DoChangePrice extends Command<Storefront> {

  private Input<String> _prodKey;
  private Input<Integer> _newPrice;
  
  public DoChangePrice(Storefront receiver) {
    super(Label.CHANGE_PRICE, receiver);
    _prodKey = _form.addStringInput(Message.requestProductKey());
    _newPrice = _form.addIntegerInput(Message.requestPrice());
  }

  @Override
  public final void execute() throws DialogException {
    try{
      _form.parse();
      _receiver.changeProductPrice(_prodKey.value(), _newPrice.value());  
    } catch(InvalidProductKeyException e){
      throw new UnknownProductKeyException(e.getInvalidKey());
    }   
  }
}
