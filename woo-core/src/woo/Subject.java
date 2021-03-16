package woo;

/**
 * Subjects are tracked by observers, notifying them when
 * a change occurs. This design pattern is used in order 
 * to implement a notification system that is flexible 
 * enough to support new delivery methods (the default is the terminal).
 */
public interface Subject {
	/** Add a new observer to the Subject.
    *   @param o new observer (Observer).
    */
    public void registerObserver(Observer o);

    /** Remove an observer from the Subject.
    *   @param o removed observer (Observer).
    */
    public void removeObserver(Observer o);

    /** Notify all Subject's Obeservers under the specified
    *	change on the Subject.
    * 	@param type type of notification (String).
    */
    public void notifyObservers(String type);
}