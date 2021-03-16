package woo;

/**
 * Observers track subjects and wait for updates.
 * When an update occurs, these observers are notified.
 * This design pattern is used in order to implement a
 * notification system that is flexible enough to support
 * new delivery methods (the default is the terminal).
 */
public interface Observer {
	/** Updates the Observer with new 
	*	information from the Subject.
	*/
    public void update(Product product, String type);
}