package woo.app.products;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
//import other classes
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.exceptions.InvalidProductKeyException;
import woo.exceptions.InvalidSupplierKeyException;
/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<Storefront> {
  private Input<String> _key, _supplierKey, _title, _author, _isbn;
  private Input<Integer> _price, _critVal; 
  
  //add input fields

  public DoRegisterProductBook(Storefront receiver) {
    super(Label.REGISTER_BOOK, receiver);
    _key = _form.addStringInput(Message.requestProductKey());
    _title = _form.addStringInput(Message.requestBookTitle());
    _author = _form.addStringInput(Message.requestBookAuthor());
    _isbn = _form.addStringInput(Message.requestISBN());
    _price = _form.addIntegerInput(Message.requestPrice());
    _critVal = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public final void execute() throws DialogException {
    try{
    _form.parse();
    _receiver.registerBook(_key.value(), _title.value(), _author.value(), _isbn.value(), _price.value(), _critVal.value(), _supplierKey.value());
    } catch(InvalidSupplierKeyException e){
      throw new UnknownSupplierKeyException(e.getInvalidKey());
    } catch(InvalidProductKeyException e){
      throw new DuplicateProductKeyException(e.getInvalidKey());
    }
  }
}
