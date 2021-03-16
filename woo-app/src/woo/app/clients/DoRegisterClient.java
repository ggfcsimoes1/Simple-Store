package woo.app.clients;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
//import other classes
import woo.app.exceptions.DuplicateClientKeyException;
import woo.exceptions.InvalidClientKeyException;
/**
 * Register new client.
 */
import woo.Storefront;

public class DoRegisterClient extends Command<Storefront> {

  private Input<String> _key;
  private Input<String> _name;
  private Input<String> _addr;


  public DoRegisterClient(Storefront storefront) {
    super(Label.REGISTER_CLIENT, storefront);
    _key = _form.addStringInput(Message.requestClientKey());
    _name = _form.addStringInput(Message.requestClientName());
    _addr = _form.addStringInput(Message.requestClientAddress());
  }

  @Override
  public void execute() throws DialogException {
  	try{
	    _form.parse();
	    _receiver.registerClient(_key.value(), _name.value(), _addr.value());
	  } catch (InvalidClientKeyException e){
		  throw new DuplicateClientKeyException(e.getInvalidKey());
	  }
  }

}
