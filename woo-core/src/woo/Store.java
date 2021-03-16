package woo;

import java.io.*;
import woo.exceptions.*;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.regex.*;
import java.util.Comparator;

/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

	// define more attributes
	private int _date = 0;
	private int _transactionCounter = 0;
	private double _accountingBalance = 0;
	private double _availableBalance = 0;


	private Map<String, Product> _productList = new TreeMap<String, Product>(new CaseInsensitiveComparator());
	private Map<String, Client> _clientList = new TreeMap<String, Client>(new CaseInsensitiveComparator());
	private Map<String, Supplier> _supplierList = new TreeMap<String, Supplier>(new CaseInsensitiveComparator());
	private ArrayList<Transaction> _transactionList = new ArrayList<Transaction>();


	protected class CaseInsensitiveComparator implements Comparator<String>, Serializable{
		
		private static final long serialVersionUID = 202009192016L;

		@Override
		public int compare(String s1, String s2){
			return s1.compareToIgnoreCase(s2);
		}
	}

	/** Loads a textfile and grabs it's fields, registering their info
	 *  in the system.
	 *  @param txtfile filename to be loaded.
	 *  @throws IOException
	 *  @throws BadEntryException
	 */
	protected void importFile(String txtfile) throws IOException, BadEntryException{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(txtfile));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\\|");
				registerFromFields(fields);
			}
			reader.close();
		} catch (BadEntryException | IOException e) {
			e.printStackTrace();
		}
					
	}

	/** Registers the fields grabbed in the system.
	 *  @param fields array with the fields to be used in the registration process.
	 *  @throws BadEntryException
	 */
	protected void registerFromFields(String[] fields) throws BadEntryException {		
		Pattern patAgent = Pattern.compile("^(CLIENT|SUPPLIER)");
		Pattern patBox = Pattern.compile("^(BOX)");
		Pattern patContainer = Pattern.compile("^(CONTAINER)"); 
		Pattern patBook = Pattern.compile("^(BOOK)");

		try {
			if (patAgent.matcher(fields[0]).matches()) {
				registerAgent(fields);
			} else if (patBox.matcher(fields[0]).matches()) {
				registerBox(fields);
			} else if (patContainer.matcher(fields[0]).matches()) {
				registerContainer(fields);
			} else if (patBook.matcher(fields[0]).matches()) {
				registerBook(fields);
			}
		} catch (BadEntryException e){
			throw e;
		}
	}

	/** Registers an agent (CLIENT/SUPPLIER) with 
	 * 	the fields grabbed in the system.
	 *  @param fields array with the fields to be used in the registration process.
	 *  @throws BadEntryException
	 */
	protected void registerAgent(String[] fields) throws BadEntryException{
		try {
			if (fields[0].equals("CLIENT")) {
				Client client = new Client(fields[1], fields[2], fields[3], getAllProducts());
				addClient(client);

			} else if (fields[0].equals("SUPPLIER")) {
				Supplier supplier = new Supplier(fields[1], fields[2], fields[3]);
				addSupplier(supplier);
			} else {
				throw new BadEntryException(fields[0]);
			}
		} catch(InvalidClientKeyException | InvalidSupplierKeyException e){
			throw new BadEntryException(fields[1]);
		}
	}

	/** Registers a box with the fields grabbed in the system.
	 *  @param fields array with the fields to be used in the registration process.
	 *  @throws BadEntryException
	 */
	protected void registerBox(String[] fields) throws BadEntryException{
		if (fields[0].equals("BOX")) {
			try{
				Product box = new Box(fields[1], fields[2], fields[3], Integer.parseInt(fields[4]), Integer.parseInt(fields[5]), Integer.parseInt(fields[6]), getAllClients());
				addProduct(box);
			} catch(InvalidProductKeyException | InvalidSupplierKeyException e){
				throw new BadEntryException(fields[1]);
			}
		} else {
			throw new BadEntryException(fields[0]);
		}
	}

	/** Registers a container with the fields grabbed in the system.
	 *  @param fields array with the fields to be used in the registration process.
	 *  @throws BadEntryException
	 */
	protected void registerContainer(String[] fields) throws BadEntryException{
		if (fields[0].equals("CONTAINER")) {
			try{
				Product container = new Container(fields[1], fields[2], fields[3], fields[4], Integer.parseInt(fields[5]), Integer.parseInt(fields[6]), Integer.parseInt(fields[7]), getAllClients());
				addProduct(container);
			} catch(InvalidProductKeyException | InvalidSupplierKeyException e){
				throw new BadEntryException(fields[1]);
			}

		} else {
			throw new BadEntryException(fields[0]);
		}
	}

	/** Registers a book with the fields grabbed in the system.
	 *  @param fields array with the fields to be used in the registration process.
	 *  @throws BadEntryException
	 */
	protected void registerBook(String[] fields) throws BadEntryException{
		if (fields[0].equals("BOOK")) {
			try{
				Product book = new Book(fields[1], fields[2], fields[3], fields[4], 
					fields[5], Integer.parseInt(fields[6]), Integer.parseInt(fields[7]), Integer.parseInt(fields[8]), getAllClients());
					addProduct(book);
			} catch(InvalidProductKeyException | InvalidSupplierKeyException e){
				throw new BadEntryException(fields[1]);
			}
		} else {
			throw new BadEntryException(fields[0]);
		}
	}

	/** Places a product in the system.
	 *  @param product product to be placed.
	 *  @throws InvalidProductKeyExcpetion
	 *  @throws InvalidSupplierKeyExcpetion
	 */
	protected void addProduct(Product product) throws InvalidProductKeyException, InvalidSupplierKeyException{
		if (_productList.containsKey(product.getKey())){
			throw new InvalidProductKeyException(product.getKey());

		} 
		if(!_supplierList.containsKey(product.getSupplierKey())){
			throw new InvalidSupplierKeyException(product.getSupplierKey());
		} else {
			_productList.put(product.getKey(), product);
		}	
	}

	/** Places a box in the system.
	 *  @param box supplier to be placed.
	 *  @throws InvalidProductKeyException
	 *  @throws InvalidSupplierKeyException
	 *  @throws InvalidServiceTypeException
	 */
	protected void addBox(Box box) throws InvalidProductKeyException, InvalidSupplierKeyException, InvalidServiceTypeException{
		if (!box.isValidType())
			throw new InvalidServiceTypeException(box.getServiceType());
		else{
			addProduct(box);
		}
	}

	/** Places a container in the system.
	 *  @param container supplier to be placed.
	 *  @throws InvalidProductKeyException
	 *  @throws InvalidSupplierKeyException
	 *  @throws InvalidServiceTypeException
	 *  @throws InvalidServiceLevelException
	 */
	protected void addContainer(Container container) throws InvalidProductKeyException, InvalidSupplierKeyException, InvalidServiceTypeException, InvalidServiceLevelException{
		if (!container.isValidLevel())
			throw new InvalidServiceLevelException(container.getServiceLevel());
		else{
			addBox(container);
		}
	}


	/** Places a supplier in the system.
	 *  @param supplier supplier to be placed.
	 *  @throws InvalidSupplierKeyExcpetion
	 */
	protected void addSupplier(Supplier sup) throws InvalidSupplierKeyException{
		if (_supplierList.containsKey(sup.getKey())){
			throw new InvalidSupplierKeyException(sup.getKey());
		} else {
			_supplierList.put(sup.getKey(), sup);
		}
	}

	/** Places a client in the system.
	 *  @param client client to be placed.
	 *  @throws InvalidClientKeyExcpetion
	 */
	protected void addClient(Client client) throws InvalidClientKeyException{
		if (_clientList.containsKey(client.getKey())){
			throw new InvalidClientKeyException(client.getKey());
		} else {
			_clientList.put(client.getKey(), client);
		}
	}

	/** Places a sale in the system.
	 *  @param sale sale to be placed.
	 *  @throws InvalidProductKeyException
	 *  @throws InvalidClientKeyExcpetion
	 *  @throws UnavailableStockException
	 */
	protected void addSale(Sale sale) throws InvalidProductKeyException, InvalidClientKeyException, UnavailableStockException{
		try{
			Product p = getProduct(sale.getProductKey());
			Client c = getClient(sale.getClientKey());
			int leftoverStock = p.getStock() - sale.getAmount();
			if (leftoverStock < 0)
				throw new UnavailableStockException(sale.getProductKey(), sale.getAmount(), p.getStock()); 

			_transactionList.add(sale);
			p.setStock(leftoverStock);
			c.addPurchase(sale);
			_transactionCounter++; //used to maintain a unique transaction ID

		} catch(InvalidProductKeyException | InvalidClientKeyException e){
			throw e;
		}
	}

	/** Places an order in the system.
	 *  @param order order to be placed.
	 *  @throws InvalidProductKeyException
	 *  @throws InvalidSupplierKeyExcpetion
	 */	
	protected void addOrder(Order order) throws InvalidProductKeyException, InvalidSupplierKeyException{
		try{
			Supplier s = getSupplier(order.getSupplierKey());
			Product p;
			_transactionList.add(order);

			for(String key : order.getAllProductKeys()){
				p = getProduct(key);
				p.setStock(p.getStock() + order.getProductAmount(key));
			}
			s.addOrder(order);
			_transactionCounter++;
			_availableBalance -= order.getBaseValue();
			_accountingBalance -= order.getBaseValue();


		} catch(InvalidProductKeyException | InvalidSupplierKeyException e){
			throw e;
		}
	}

	/** Adds a product to an order.
	 *  @param prod product to be added.
	 *  @param amount stock to add.
	 *  @param supplierKey the supplier of the products.
	 *  @param order order to be placed.
	 *  @throws InvalidProductSupplierException
	 */
	protected void addProductToOrder(Product prod, int amount, String supplierKey, Order order) throws InvalidProductSupplierException{
		if(!prod.getSupplierKey().equals(supplierKey)){
			throw new InvalidProductSupplierException(supplierKey, prod.getKey());
		}
		order.addProduct(prod, amount);
		order.setBaseValue(order.getBaseValue() + amount * prod.getPrice());

	}

	/** Advances a date.
	 *  @param daysToAdvance days to advance the date.
	 *  @throws NegativeDateException
	 */
	protected void advanceDate(int daysToAdvance) throws NegativeDateException{
		if (daysToAdvance > 0){
			_date = _date + daysToAdvance;
		} else {
			throw new NegativeDateException(daysToAdvance);
		}
	}

	/** Gets the system's current date.
	 *  @return current date.
	 */	
	protected int getDate(){
		return _date;
	}

	/** Gets the system's available balance.
	 *  @return available balance.
	 */
	protected double getAvailableBalance(){
		return _availableBalance;
	}

	/** Gets the system's accounting balance.
	 *  @return accounting balance.
	 */
	protected double getAccountingBalance(){
		double balance = 0; 
		balance += _accountingBalance;
		for(Client c: _clientList.values())
			for(Sale s: c.getPurchases()){
				s.setDateOfSimulation(_date); //sets a temporary date in order to get an accurate price estimate
				balance += s.getPaidPrice();
			}
		return balance;
	}

	/** Given a key, returns a client.
	 * 	@param key client key (String).
	 *  @return the client with given key.
	 * 	@throws InvalidClientKeyException
	 */		
	protected Client getClient(String key) throws InvalidClientKeyException{
		Client client = _clientList.get(key);
		if(client == null){
			throw new InvalidClientKeyException(key);
		}
		return client;     
	}

	/** Given a key, returns all client's transactions.
	*	@param clientKey client key (String).
	*	@return List of client's transactions (List).
	* 	@throws InvalidClientKeyException
	*/
	protected List<Sale> getClientTransactions(String clientKey) throws InvalidClientKeyException{
		try{
			return getClient(clientKey).getPurchases();
		} catch(InvalidClientKeyException e){
			throw e;
		}
	}

	/** Given a key, returns all client's paid transactions.
	*	@param clientKey client key (String).
	*	@return List of client's paid transactions (List).
	* 	@throws InvalidClientKeyException
	*/
	protected List<Sale> getClientPaidTransactions(String clientKey) throws InvalidClientKeyException{
		try{
			return getClient(clientKey).getPaidPurchases();
		} catch(InvalidClientKeyException e){
			throw e;
		}
	}

	/** Returns an unmodifiable sorted list with
	 *  every client, to be used by the application.
	 *  @return unmodifiable list of all client.
	 */	
	protected List<Client> getAllClients(){
		List<Client> lst = new LinkedList<Client>();
		lst.addAll(_clientList.values());
		Collections.sort(lst);    
		return Collections.unmodifiableList(lst);
	}

	/** Aux function used to get a product given
	 *  a certain productKey.
	 *  @return Product with the given key.
	 * 	@throws InvalidProductKeyException
	 */	
	protected Product getProduct(String productKey) throws InvalidProductKeyException{
		Product product = _productList.get(productKey);
		if(product == null){
			throw new InvalidProductKeyException(productKey);
		}
		return product;  
	}

	/** Given a price limit, returns all products under the given price.
	*	@param priceLimit price limit (int).
	*	@return List of products (List).
	*/
	protected List<Product> getAllProductsUnderPriceLimit(int priceLimit){
		List<Product> lst = new LinkedList<>();
		for(String key : _productList.keySet()){
			Product p = _productList.get(key);
			if(p.getPrice() < priceLimit){
				lst.add(p);
			}
		}
		return Collections.unmodifiableList(lst);
	}

	/** Returns an unmodifiable sorted list with
	 *  every product, to be used by the application.
	 *  @return unmodifiable list of all products.
	 */	
	protected List<Product> getAllProducts(){
		List<Product> lst = new LinkedList<Product>();
		lst.addAll(_productList.values());
		Collections.sort(lst);
		return Collections.unmodifiableList(lst);
	}

	/** Aux function used to get a product given
	 *  a certain productKey.
	 *  @return Product with the given key.
	 * 	@throws InvalidSupplierKeyException
	 */	
	protected Supplier getSupplier(String supplierKey) throws InvalidSupplierKeyException{
		Supplier supplier = _supplierList.get(supplierKey);
		if(supplier == null){
			throw new InvalidSupplierKeyException(supplierKey);
		}
		return supplier;  
	}

	/** Given a key, returns all supplier's transactions.
	*	@param supplierKey supplier key (String).
	*	@return List of supplier's transactions (List).
	* 	@throws InvalidSupplierKeyException
	*/
	protected List<Order> getSupplierTransactions(String supplierKey) throws InvalidSupplierKeyException{
		try{
			return getSupplier(supplierKey).getOrders();
		} catch(InvalidSupplierKeyException e){
			throw e;
		}
	}

	/** Returns an unmodifiable sorted list with
	 *  every supplier, to be used by the application.
	 *  @return unmodifiable list of all suppliers.
	 */	
	protected List<Supplier> getAllSuppliers(){
		List<Supplier> lst = new LinkedList<Supplier>();
		lst.addAll(_supplierList.values());
		Collections.sort(lst);
		return Collections.unmodifiableList(lst);
	}

	/** Returns number of transactions in the system.
	*	@return transaction counter.
	*/
	protected int getTransactionCounter(){
		return _transactionCounter;
	}

	/** Aux function used to get a transaction given
	 *  a certain transactionKey.
	 *  @return Transaction with the given key.
	 * 	@throws InvalidTransactionKeyException
	 */	
	protected Transaction getTransaction(int transactionKey) throws InvalidTransactionKeyException{
		try{
			Transaction transaction = _transactionList.get(transactionKey);
			if(transaction == null){
				throw new InvalidTransactionKeyException(transactionKey);
			}
			return transaction;  
		}catch (IndexOutOfBoundsException e){
			throw new InvalidTransactionKeyException(transactionKey);
		}
		
	}

	/** Given a key, returns all client's notifications.
	*	@param clientKey client key (String).
	*	@return List of client's notifications (List).
	* 	@throws InvalidClientKeyException
	*/
	protected List<Notification> getAllNotifications(String clientKey) throws InvalidClientKeyException{
		try{
			return getClient(clientKey).getNotifications();
		} catch(InvalidClientKeyException e){
			throw e;
		}	
	}

	/** Changes a products price.
	*	@param key products key.
	*	@param newPrice new products price.
	*	@throws InvalidProductKeyException
	*/
	protected void changeProductPrice(String key, int newPrice) throws InvalidProductKeyException{
		try{
			Product product = getProduct(key);
			if (newPrice <= 0){
				return; // Fails silently...
			} else {
				product.setPrice(newPrice);
			}
		}catch(InvalidProductKeyException e){
			throw e;
		}	
	}

	/** Enable/Desable supplier transactions.
	*	@param supplierKey supplier key (String).
	*	@throws InvalidSupplierKeyException
	*/
	protected void toggleSupplierTransactions(String supplierKey) throws InvalidSupplierKeyException{
		try{
			getSupplier(supplierKey).toggleStatus();
		} catch(InvalidSupplierKeyException e){
			throw e;
		}
	}

	/** Given a key, pays the respective transaction.
	*	@param transactionKey transaction key (int).
	*	@throws InvalidTransactionKeyException
	*/
	protected void paySale(int transactionKey) throws InvalidTransactionKeyException{
		try{
			Transaction transaction = getTransaction(transactionKey);
			if(!transaction.paid()){
				Sale sale = (Sale) transaction;
				double toPay = sale.estimatePaymentValue(_date);
				double awardedPoints = sale.estimatePurchasePoints(_date, toPay);
				sale.pay(toPay, awardedPoints, _date);
				_availableBalance += toPay; 
			}
		} catch(InvalidTransactionKeyException e){
			throw e;
		}
	}

	/** Given a key, return the respective transaction.
	*	@param transactionKey transaction key (int).
	*	@throws InvalidTransactionKeyException
	*/
	protected String showTransaction(int transactionKey) throws InvalidTransactionKeyException{
		try{
			Transaction t = getTransaction(transactionKey);
			if(!t.paid())
				t.setDateOfSimulation(getDate());
			return t.toString();
		} catch(InvalidTransactionKeyException e){
			throw e;
		}
	}

	/** Given a client key and a product key, Enable/Disable the 
	*	client's notifications from the product.
	*	@param clientKey client key (String).
	*	@param productKey product key (String).
	*	@throws InvalidClientKeyException
	*	@throws InvalidProductKeyException
	*/
	public boolean toggleProductNotifications(String clientKey, String productKey) throws InvalidClientKeyException, InvalidProductKeyException{
		try{
			Client c = getClient(clientKey);
			Product p = getProduct(productKey);
			if(p.isObserver(c)){
				p.removeObserver(c);
				return false;
			}
			p.registerObserver(c);
			return true;
		} catch(InvalidClientKeyException | InvalidProductKeyException e){
			throw e;
		}
	}	

	public Client getLeastActiveClient(){
		List<Client> clientList = getAllClients();
		double min = clientList.get(0).getPaidPurchasesValue();
		Client minClient = clientList.get(0);
		
		for(Client c: clientList){
			if(c.getPaidPurchasesValue() < min){
				min = c.getPaidPurchasesValue();
				minClient = c;
			}
		}
		return minClient;
	}

	public Product getBestProduct(){
		double max = 0;
		Product maxProduct = _productList.get(0);
		for(Product p: getAllProducts()){
			if(p.getCritVal() - p.getStock() > max){
				max = p.getCritVal() - p.getStock();
				maxProduct = p;
			}
		}
		return maxProduct;
	}

	public Product getProductWithMaxStock(){
		int max = 0;
		Product maxProduct = _productList.get(0);
		for(Product p: getAllProducts()){
			if(p.getStock() > max){
				maxProduct = p;
			}
		}
		return maxProduct;
	}
}
