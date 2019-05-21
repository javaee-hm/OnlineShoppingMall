package Bean;


public class BeanOrderDetail {
	int detail_id;
	int order_id;
	int book_id;
	int num;
	float detail_price;
	private BeanBook bookDetails;
	
	public BeanBook getBookDetails() {
		return bookDetails;
	}
	public void setBookDetails(BeanBook bookDetails) {
		this.bookDetails = bookDetails;
	}
	public int getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(int detail_id) {
		this.detail_id = detail_id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public float getDetail_price() {
		return detail_price;
	}
	public void setDetail_price(float detail_price) {
		this.detail_price = detail_price;
	}
	
	
}
