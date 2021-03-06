package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
//import other classes
import woo.exceptions.UnavailableFileException;
import woo.app.exceptions.FileOpenFailedException;

/**
 * Open existing saved state.
 */
public class DoOpen extends Command<Storefront> {

  private Input<String> _filename;

  /** @param receiver */
  public DoOpen(Storefront receiver) {
    super(Label.OPEN, receiver);
    _filename = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try{
      _form.parse();
      _receiver.load(_filename.value());
    } catch (UnavailableFileException e){
      throw new FileOpenFailedException(e.getFilename());
    }
  }

}
