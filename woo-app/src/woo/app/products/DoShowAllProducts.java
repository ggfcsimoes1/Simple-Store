package woo.app.products;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;      
import pt.tecnico.po.ui.Input;                                                                                                                                                                                                               
import woo.Storefront;                                                                                                                        
//import other classes
import woo.Product;

/**
 * Show all products.
 */
public class DoShowAllProducts extends Command<Storefront> {

  public DoShowAllProducts(Storefront receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
    //FIXME init input fields
  }

  @Override
  public final void execute() throws DialogException {
      for(Product prod: _receiver.getAllProducts()){
        _display.addLine(prod.toString());
      }
      _display.display();
      _display.popup("Melhor produto: " + _receiver.getBestProduct());
      _display.popup("Produto com maior stock: " + _receiver.getProductWithMaxStock());
  }
}

