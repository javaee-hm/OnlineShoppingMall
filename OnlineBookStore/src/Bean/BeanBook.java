package Bean;

import java.util.Date;



public class BeanBook implements Comparable{
	int book_id;
	String title;
	String author;
	String publisher;
	Date publishing_time;
	float book_price;
	int category_id;
	int inventory;
	String description;
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public Date getPublishing_time() {
		return publishing_time;
	}
	public void setPublishing_time(Date publishing_time) {
		this.publishing_time = publishing_time;
	}
	public float getBook_price() {
		return book_price;
	}
	public void setBook_price(float book_price) {
		this.book_price = book_price;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		BeanBook n = (BeanBook) o;
        int lastCmp = title.compareTo(n.title);
        return (lastCmp);
	}
	
}
