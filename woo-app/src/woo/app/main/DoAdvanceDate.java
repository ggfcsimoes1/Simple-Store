package woo.app.main;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        

//import other classes
import woo.app.exceptions.InvalidDateException;
import woo.exceptions.NegativeDateException;

/**
 * Advance current date.
 */
public class DoAdvanceDate extends Command<Storefront> {
  
  private Input<Integer> _daysToAdvance;

  public DoAdvanceDate(Storefront receiver) {
    super(Label.ADVANCE_DATE, receiver);
    _daysToAdvance = _form.addIntegerInput(Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws DialogException {
    try {
        _form.parse();
        _receiver.advanceDate(_daysToAdvance.value());
    } catch(NegativeDateException e) { 
        throw new InvalidDateException(e.getBadDate());
    }
  }
}

