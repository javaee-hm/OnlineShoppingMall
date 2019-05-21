package Impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Bean.BeanBook;
import Dao.BookDao;
import Service.BookService;
import cart.ShoppingCart;
import exception.BookNotFoundException;
import exception.BooksNotFoundException;
import exception.OrderException;
@Service("BookServiceImpl")
public class BookServiceImpl implements BookService {
	@Resource(name="BookDAO")
	BookDao bookDao;

	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public void addBook(BeanBook book) {
		// TODO Auto-generated method stub
		bookDao.addBook(book);
		return;
	}

	@Override
	public void deleteBook(int bookid) {
		// TODO Auto-generated method stub
		bookDao.deleteBook(bookid);
		return;
	}

	@Override
	public void updateBook(BeanBook book) {
		// TODO Auto-generated method stub
		bookDao.updateBook(book);
		return;
	}

	@Override
	public int getNumberOfBooks() throws BooksNotFoundException {
		// TODO Auto-generated method stub
		return bookDao.getNumberOfBooks();
	}

	@Override
	public Collection<BeanBook> getBooks() throws BooksNotFoundException {
		// TODO Auto-generated method stub
		return bookDao.getBooks();
	}

	@Override
	public BeanBook getBookDetails(int bookId) throws BookNotFoundException {
		// TODO Auto-generated method stub
		return bookDao.getBookDetails(bookId);
	}

	@Override
	public void buyBooks(ShoppingCart cart) throws OrderException {
		// TODO Auto-generated method stub
		bookDao.buyBooks(cart);
	}

	@Override
	public void buyBook(int bookId, int quantity) throws OrderException {
		// TODO Auto-generated method stub
		bookDao.buyBook(bookId, quantity);;
	}
	
}
