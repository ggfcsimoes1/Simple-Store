package woo;

import java.util.List;

/** 
* A book is an object which differs from a standard product in 3 fields: <p>
* It has a title, an author, and an International Standard Book Number (ISBN).
*/
public class Book extends Product{

    private String _title = "";
    private String _author = "";
    private String _isbn = "";

    /** User level constructor used to create a book.
    * @param key book's key.
    * @param title book's title.
    * @param author book's author.
    * @param isbn book's isbn.
    * @param price book's price.
    * @param critVal book's critic value.
    * @param supplierKey book's supplier key.
    */
    Book(String key, String title, String author, String isbn, int price, int critVal, String supplierKey, List<Client> clients){
        super(key, supplierKey, price, critVal, clients);
        this.setPaymentPeriod(3); //a book has a 3 day payment period
        _title = title;
        _author = author;
        _isbn = isbn;

    }

    /** Application level constructor used to create a book from the given import file.
    *   @param key book key.
    *   @param title book title.
    *   @param author book's author.
    *   @param isbn book's isbn.
    *   @param supplierKey book's supplier key.
    *   @param price book's price.
    *   @param critVal book's critic value.
    *   @param stock amount in stock.
    */
    Book(String key, String title, String author, String isbn, String supplierKey, int price, int critVal, int stock, List<Client> clients){
        super(key, supplierKey, price, critVal, stock, clients);
        this.setPaymentPeriod(3);
        _title = title;
        _author = author;
        _isbn = isbn;
    }

    /** Returns all of the book's information in a string, to be displayed.
    *   @return Books attributes in the desired format.
    */
    @Override
    public String toString(){
        return "BOOK|" + super.toString() + "|" + _title + "|" + _author + "|" + _isbn;
    }
}
