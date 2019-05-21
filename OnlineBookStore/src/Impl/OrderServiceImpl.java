package Impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Bean.BeanOrder;
import Dao.OrderDao;
import Service.OrderService;
import cart.ShoppingCart;
import exception.BooksNotFoundException;
@Service("OrderServiceImpl")
public class OrderServiceImpl implements OrderService {
	@Resource(name="OrderDAO")
	OrderDao orderDao;
	
	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	@Override
	public void AddOrder(String userid, ShoppingCart cart, String address) throws Exception {
		// TODO Auto-generated method stub
		orderDao.AddOrder(userid, cart, address);
	}

	@Override
	public Collection<BeanOrder> getOrders(String userid, boolean withDetails) throws BooksNotFoundException {
		// TODO Auto-generated method stub
		return orderDao.getOrders(userid, withDetails);
	}

	@Override
	public void finishOrder(int orderid) {
		// TODO Auto-generated method stub
		orderDao.finishOrder(orderid);
	}

}
