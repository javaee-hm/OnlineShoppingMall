package Service;

import java.util.Collection;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import Bean.BeanBook;
import cart.ShoppingCart;
import exception.BookNotFoundException;
import exception.BooksNotFoundException;
import exception.OrderException;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
public interface BookService {
	public void addBook(BeanBook book);
	public void deleteBook(int bookid) ;
	public void updateBook(BeanBook book) ;
	public int getNumberOfBooks() throws BooksNotFoundException;
	public Collection<BeanBook> getBooks() throws BooksNotFoundException;
	public BeanBook getBookDetails(int bookId) throws BookNotFoundException;
	public void buyBooks(ShoppingCart cart) throws OrderException;
	public void buyBook(int bookId, int quantity) throws OrderException;
	
}
