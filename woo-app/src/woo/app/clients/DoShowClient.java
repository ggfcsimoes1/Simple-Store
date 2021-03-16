package woo.app.clients;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
//import other classes
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.InvalidClientKeyException;
import woo.Notification;
import java.util.ArrayList;
import woo.Sale;
/**
 * Show client.
 */
public class DoShowClient extends Command<Storefront> {

  private Input<String> _key;

  public DoShowClient(Storefront storefront) {
    super(Label.SHOW_CLIENT, storefront);
    _key = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
  	try{
      double avgPVal = 0;
      _form.parse();
      _display.addLine(_receiver.showClient(_key.value()));
      for(Sale s: _receiver.getClientTransactions(_key.value())){
        avgPVal += s.getPaidPrice();
      }
      avgPVal = avgPVal / _receiver.getClientTransactions(_key.value()).size();
      _display.addLine("VALOR MÉDIO: " + avgPVal);
      
      for(Notification not: _receiver.getAllNotifications(_key.value())){
        
        if (!not.seen())
          _display.addLine(not.toString());
          _receiver.seeNotification(not);
      }
      _display.display();
  	} catch(InvalidClientKeyException e){
      throw new UnknownClientKeyException(e.getInvalidKey());
  	}
  }

}
