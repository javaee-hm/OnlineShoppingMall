package Impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Bean.BeanOrderDetail;
import Dao.OrderDetailDao;
import Service.OrderDetailService;
import exception.BookNotFoundException;
@Service("OrderDetailServiceImpl")
public class OrderDetailServiceImpl implements OrderDetailService {
	@Resource(name="OrderDetailDAO")
	OrderDetailDao orderDetailsDAO;
	
	public OrderDetailDao getOrderDetailsDAO() {
		return orderDetailsDAO;
	}

	public void setOrderDetailsDAO(OrderDetailDao orderDetailsDAO) {
		this.orderDetailsDAO = orderDetailsDAO;
	}

	@Override
	public Collection<BeanOrderDetail> getOrderDetails(int orderId) throws BookNotFoundException {
		// TODO Auto-generated method stub
		return orderDetailsDAO.getOrderDetails(orderId);
	}

}
