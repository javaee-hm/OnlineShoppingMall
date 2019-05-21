package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import Bean.BeanBook;
import cart.ShoppingCart;
import cart.ShoppingCartItem;
import exception.BookNotFoundException;
import exception.BooksNotFoundException;
import exception.OrderException;

public class BookDao {
	ArrayList<BeanBook>books;
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void addBook(BeanBook book) {
		String sql="insert into books(title,author,publisher,publishing_time,book_price,category_id,inventory,description) values(?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[]{book.getTitle(),book.getAuthor(),book.getPublisher(),book.getPublishing_time(),book.getBook_price(),book.getCategory_id(),book.getInventory(),book.getDescription()});
	}
	public void deleteBook(int bookid) {
		String sql="delete from books where book_id=?";
		jdbcTemplate.update(sql, new Object[]{bookid});
	}
	public void updateBook(BeanBook book) {
		String sql="update books set title=?,author=?,publisher=?,publishing_time=?,book_price=?,category_id=?,inventory=?,description=? where book_id=?";
		jdbcTemplate.update(sql, new Object[]{book.getTitle(),book.getAuthor(),book.getPublisher(),book.getPublishing_time(),book.getBook_price(),book.getCategory_id(),book.getInventory(),book.getDescription(),book.getBook_id()});
	}
	public int getNumberOfBooks() throws BooksNotFoundException {
		books = new ArrayList();
		String selectStatement = "select * " + "from books";
		this.jdbcTemplate.query(selectStatement, new ResultSetExtractor<BeanBook>() {
			@Override
			public BeanBook extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				while (rs.next()) {
					BeanBook bd = new BeanBook();
					bd.setBook_id(rs.getInt(1)); 
					bd.setTitle(rs.getString(2));
					bd.setAuthor(rs.getString(3));
					bd.setPublisher(rs.getString(4));
					bd.setPublishing_time(rs.getDate(5));
					bd.setBook_price(rs.getFloat(6));
					bd.setCategory_id(rs.getInt(7));
					bd.setInventory(rs.getInt(8));
					bd.setDescription(rs.getString(9));
					if (rs.getInt(8) > 0)
						books.add(bd);
				}
				return null;
			}
		});
		return books.size();
	}
	public Collection<BeanBook> getBooks() throws BooksNotFoundException {
		books = new ArrayList<>();
		String selectStatement = "select * " + "from books";
		this.jdbcTemplate.query(selectStatement, new ResultSetExtractor<BeanBook>() {
			@Override
			public BeanBook extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				while (rs.next()) {
					BeanBook bd = new BeanBook();
					bd.setBook_id(rs.getInt(1)); 
					bd.setTitle(rs.getString(2));
					bd.setAuthor(rs.getString(3));
					bd.setPublisher(rs.getString(4));
					bd.setPublishing_time(rs.getDate(5));
					bd.setBook_price(rs.getFloat(6));
					bd.setCategory_id(rs.getInt(7));
					bd.setInventory(rs.getInt(8));
					bd.setDescription(rs.getString(9));
					if (rs.getInt(8) > 0)
						books.add(bd);
				}
				return null;
			}
		});
		Collections.sort(books);
		return books;
	}
	
	public BeanBook getBookDetails(int bookId) throws BookNotFoundException {
		String selectStatement = "select * " + "from books where book_id = ? ";
		BeanBook bookDetails = this.jdbcTemplate.query(selectStatement,new Object[] { bookId }, new ResultSetExtractor<BeanBook>() {
			@Override
			public BeanBook extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				if (rs.next()) {
					BeanBook bd = new BeanBook();
					bd.setBook_id(rs.getInt(1)); 
					bd.setTitle(rs.getString(2));
					bd.setAuthor(rs.getString(3));
					bd.setPublisher(rs.getString(4));
					bd.setPublishing_time(rs.getDate(5));
					bd.setBook_price(rs.getFloat(6));
					bd.setCategory_id(rs.getInt(7));
					bd.setInventory(rs.getInt(8));
					bd.setDescription(rs.getString(9));
					return bd;
				}
				return null;
			}
		});
		if(bookDetails==null)throw new BookNotFoundException("Couldn't find book: " + bookId);
		return bookDetails;
	}
	public void buyBooks(ShoppingCart cart) throws OrderException {
		Collection items = cart.getItems();
		Iterator i = items.iterator();
		try {
			while (i.hasNext()) {
				ShoppingCartItem sci = (ShoppingCartItem) i.next();
				BeanBook bd = (BeanBook) sci.getItem();
				int id = bd.getBook_id();
				int quantity = sci.getQuantity();
				buyBook(id, quantity);
			}
		}
		catch (Exception ex) {
			throw new OrderException("Transaction failed: " + ex.getMessage());
		}
	}
	public void buyBook(int bookId, int quantity) throws OrderException {
		try {
			String selectStatement = "select * " + "from books where book_id = ? ";
			this.jdbcTemplate.query(selectStatement, new ResultSetExtractor<BeanBook>() {
				@Override
				public BeanBook extractData(ResultSet rs) throws SQLException, DataAccessException {
					// TODO Auto-generated method stub
					if (rs.next()) {
						int inventory = rs.getInt(8);
						if ((inventory - quantity) >= 0) {
							String updateStatement = "update books set inventory = inventory - ? where book_id = ?";
							jdbcTemplate.update(updateStatement, new Object[] { quantity, bookId });
						} else {
							try {
								throw new OrderException("Not enough of " + bookId + " in stock to complete order.");
							} catch (OrderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}	
					}
					return null;
				}
			}, bookId);
		} catch (Exception ex) {
			throw new OrderException("Couldn't purchase book: " + bookId + ex.getMessage());
		}
	}
}
