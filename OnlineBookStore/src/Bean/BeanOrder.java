package Bean;

import java.util.Date;
import java.util.List;



public class BeanOrder implements Comparable{
	int order_id;
	String user_id;
	Date order_time;
	float order_price;
	String address;
	boolean state;
	private List<BeanOrderDetail>orderDetails;
	
	
	public List<BeanOrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<BeanOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Date getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}
	public float getOrder_price() {
		return order_price;
	}
	public void setOrder_price(float order_price) {
		this.order_price = order_price;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		BeanOrder n = (BeanOrder) o;
        return order_id-n.order_id;
	}
	
	
}
