package woo;

import woo.exceptions.*;
import java.io.*;
// import classes (cannot import from pt.tecnico or woo.app)
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
 * Storefront: façade for the core classes.
 */
public class Storefront {

	/** Current filename. */
	private String _filename = "";

	/** The actual store. */
	private Store _store = new Store();

	/** Saves the current status of the system to a file.
	*   @throws IOException
	*   @throws FileNotFoundException
	*   @throws MissingFileAssociationException
	*/
	public void save() throws IOException, FileNotFoundException {
		try {
			if(!_filename.equals("")){
				ObjectOutputStream out =
				new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
				out.writeObject(_store);
				out.writeObject(_filename);
				out.close();
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	/** When there's no filename set, saves the store with a new filename.
	*   @param filename
	*   @throws MissingFileAssociationException
	*   @throws IOException
	*   @throws FileNotFoundException
	*/
	public void saveAs(String filename) throws FileNotFoundException, IOException {
		_filename = filename;
		save();
	}

	/** Verify whether the filename is emtpy or not.
	*	@return boolean.
	*/
	public boolean emptyFileName(){
		return _filename.equals("");
	}

	/** Given a filename, loads the data in it to the system.
	*   @param filename
	*   @throws UnavailableFileException
	*/
	public void load(String filename) throws UnavailableFileException{
		try{
			_filename = filename;
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
			_store = (Store) in.readObject();
			in.close();
		} catch(IOException | ClassNotFoundException e) {
			throw new UnavailableFileException(filename);
		}
	}

	/**
	* @param textfile
	* @throws ImportFileException
	*/
	public void importFile(String textfile) throws ImportFileException {
		try {
			_store.importFile(textfile);
		} catch (IOException | BadEntryException e) {
			throw new ImportFileException(textfile);
		}
	}

	/**
	* @see Store#avanceDate()
	*/
	public void advanceDate(int daysToAdvance) throws NegativeDateException{
		try{
			_store.advanceDate(daysToAdvance);
		} catch (NegativeDateException e){
			throw e;
		}
	}

	/**
	* @see Store#getDate()
	*/
	public int getDate(){
		return _store.getDate();
	}

	/** Gets the filename.
	* @return the filename.
	*/
	public String getFilename(){
		return _filename;
	}

	/**
	* @see Store#getAvailableBalance()
	*/
	public int getAvailableBalance(){
		return (int) _store.getAvailableBalance();
	}

	/**
	* @see Store#getAccountingBalance()
	*/
	public int getAccountingBalance(){
		return (int) _store.getAccountingBalance();
	}

	/** Registers a client.
	*   @param fields array with the fields to be used in the registration process.
	*   @throws InvalidClientKeyException
	*/
	public void registerClient(String key, String name, String addr) throws InvalidClientKeyException{
		try{
			Client c = new Client(key, name, addr, _store.getAllProducts());
			_store.addClient(c);
		} catch(InvalidClientKeyException e){
			throw e;
		}
	}

	/** Given a key, shows a client.
	*   @param key client's key.
	*   @throws InvalidClientKeyException
	*/ 
	public String showClient(String key)throws InvalidClientKeyException{
		try{
			Client c = _store.getClient(key);
			return c.toString();
		} catch(InvalidClientKeyException e) {
			throw e;
		}
	}

	/**
	* @see Store#getClientTransactions()
	*/
	public List<Sale> getClientTransactions(String clientKey) throws InvalidClientKeyException{
		try{
			return _store.getClientTransactions(clientKey);
		} catch(InvalidClientKeyException e){
			throw e;
		}
	}

	/**
	* @see Store#getClientPaidTransactions()
	*/
	public List<Sale> getClientPaidTransactions(String clientKey) throws InvalidClientKeyException{
		try{
			return _store.getClientPaidTransactions(clientKey);
		} catch(InvalidClientKeyException e){
			throw e;
		}
	}

	/**
	* @see Store#getAllClients()
	*/
	public List<Client> getAllClients(){
		return _store.getAllClients();
	}

	/**
	* @see Store#addSupplier()
	*/
	public void registerSupplier(String key, String name, String addr) throws InvalidSupplierKeyException{
		try{
			Supplier s = new Supplier(key, name, addr);
			_store.addSupplier(s);
		} catch(InvalidSupplierKeyException e){
			throw e;
		}
	}

	/**
	* @see Store#toggleSupplierTransactions()
	*/
	public boolean toggleSupplierTransactions(String supplierKey) throws InvalidSupplierKeyException{
		try{
			_store.toggleSupplierTransactions(supplierKey);
			return _store.getSupplier(supplierKey).getStatus();
		} catch(InvalidSupplierKeyException e){
			throw e;
		}
	}

	/**
	* @see Store#getSupplierTransactions()
	*/
	public List<Order> getSupplierTransactions(String supplierKey) throws InvalidSupplierKeyException{
		try{
			return _store.getSupplierTransactions(supplierKey);
		} catch(InvalidSupplierKeyException e){
			throw e;
		}
	}

	/**
	* @see Store#getAllSuppliers()
	*/
	public List<Supplier> getAllSuppliers(){
		return _store.getAllSuppliers();
	}

	/**
	* @see Supplier#getStatus()
	*/
	public boolean getSupplierStatus(Supplier supp){
		return supp.getStatus();
	}

	/**
	* @see Store#addProduct()
	*/
	public void registerBook(String key, String title, String author, String isbn, int price, 
		int critVal, String supplierKey) throws InvalidProductKeyException, InvalidSupplierKeyException{	
		try{
			Book b = new Book(key, title, author, isbn, price, critVal, supplierKey, _store.getAllClients());
			_store.addProduct(b);
		} catch(InvalidProductKeyException | InvalidSupplierKeyException e){
			throw e;
		}
	}

	/**
	* @see Store#addBox()
	*/
	public void registerBox(String key, int price, int critVal, String supplierKey, String serviceType) 
		throws InvalidProductKeyException, InvalidSupplierKeyException, InvalidServiceTypeException{
		try{
			Box b = new Box(key, price, critVal, supplierKey, serviceType, _store.getAllClients());
			_store.addBox(b);
		} catch(InvalidProductKeyException | InvalidSupplierKeyException | InvalidServiceTypeException e){
			throw e;
		}
	}

	/**
	* @see Store#addContainer()
	*/
	public void registerContainer(String key, int price, int critVal, String supplierKey, 
		String serviceType, String serviceLevel) throws InvalidProductKeyException, InvalidSupplierKeyException, 
			InvalidServiceTypeException, InvalidServiceLevelException{
		try{
			Container c = new Container(key, price, critVal, supplierKey, serviceType, serviceLevel, _store.getAllClients());
			_store.addContainer(c);
		} catch(InvalidProductKeyException | InvalidSupplierKeyException | 
				InvalidServiceTypeException | InvalidServiceLevelException e){
			throw e;
		}
	}

	/**
	* @see Store#getAllProductsUnderPriceLimit()
	*/
	public List<Product> getAllProductsUnderPriceLimit(int priceLimit){
		return _store.getAllProductsUnderPriceLimit(priceLimit);
	}

	/**
	* @see Store#getAllProducts()
	*/
	public List<Product> getAllProducts(){
		return _store.getAllProducts();
	}

	/**
	* @see Store#changeProductPrice()
	*/
	public void changeProductPrice(String key, int newPrice) throws InvalidProductKeyException{
		try{
			_store.changeProductPrice(key, newPrice);
		} catch (InvalidProductKeyException e){
			throw e;
		}
	}

	/**
	* @see Store#addSale()
	*/
	public void registerSale(String clientKey, int deadline, String productKey, int amount) 
		throws InvalidProductKeyException, InvalidClientKeyException, UnavailableStockException{
		try{
			Product p = _store.getProduct(productKey);
			Sale s = new Sale(_store.getTransactionCounter(), _store.getClient(clientKey), deadline, p, amount);
			_store.addSale(s);
		} catch(InvalidProductKeyException | InvalidClientKeyException | 
				UnavailableStockException e){
			throw e;
		}
	}

	/**
	* @see Store#addOrder()
	*/
	public void registerOrder(String supplierKey, LinkedHashMap<String, Integer> products) 
		throws InvalidSupplierKeyException, InvalidProductKeyException ,InvalidProductSupplierException, InvalidSupplierStatusException {
		try{
			Order o = new Order(_store.getTransactionCounter(), supplierKey, getDate());
			if(!_store.getSupplier(supplierKey).getStatus()){
				throw new InvalidSupplierStatusException(supplierKey);
			}
			for(String key: products.keySet()){
				_store.addProductToOrder(_store.getProduct(key), 
					products.get(key), supplierKey, o);
			}
			_store.addOrder(o);
		} catch(InvalidSupplierKeyException | InvalidProductKeyException | 
				InvalidProductSupplierException e) {
			throw e;
		} 
	}

	/**
	* @see Store#showTransaction()
	*/
	public String showTransaction(int transactionKey) throws InvalidTransactionKeyException{
		try{
			return _store.showTransaction(transactionKey);
		} catch(InvalidTransactionKeyException e) {
			throw e;
		}
	}

	/**
	* @see Store#paySale()
	*/
	public void pay(int saleKey) throws InvalidTransactionKeyException{
		try{
			_store.paySale(saleKey);
		} catch (InvalidTransactionKeyException e) {
			throw e;
		}
	}

	/**
	* @see Store#getAllNotifications()
	*/
	public List<Notification> getAllNotifications(String clientKey) throws InvalidClientKeyException{
		try{
			return _store.getAllNotifications(clientKey);
		} catch(InvalidClientKeyException e){
			throw e;
		}	
	}

	/**
	* Marks a notification as seen.
	* @param not Notification to be seen.
	*/
	public void seeNotification(Notification not){
		not.see();
	}

	/**
	* @see Store#toggleProductNotifications()
	*/
	public boolean toggleProductNotifications(String clientKey, String productKey) throws InvalidClientKeyException, InvalidProductKeyException{
		try{
			return _store.toggleProductNotifications(clientKey, productKey);
		} catch(InvalidClientKeyException | InvalidProductKeyException e){
			throw e;
		}
	}

	public String getLeastActiveClient(){
		return _store.getLeastActiveClient().toString();
	}

	public String getBestProduct(){
		return _store.getBestProduct().toString();
	}

	public String getProductWithMaxStock(){
		return _store.getProductWithMaxStock().toString();
	}

	public Supplier getSupplier(String key) throws InvalidSupplierKeyException{
		try{
			return _store.getSupplier(key);
		} catch( InvalidSupplierKeyException e){
			throw e;
		}
	}
}
