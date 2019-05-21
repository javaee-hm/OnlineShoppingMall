package Service;

import java.util.Collection;

import Bean.BeanOrder;
import cart.ShoppingCart;
import exception.BooksNotFoundException;

public interface OrderService {
	public  void AddOrder(String userid,ShoppingCart cart,String address) throws Exception;
	public Collection<BeanOrder> getOrders(String userid,boolean withDetails) throws BooksNotFoundException;
	public void finishOrder(int orderid);
}
