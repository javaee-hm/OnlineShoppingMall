package Service;

import java.util.Collection;

import Bean.BeanOrderDetail;
import exception.BookNotFoundException;

public interface OrderDetailService {
	public Collection<BeanOrderDetail> getOrderDetails(int orderId) throws BookNotFoundException;
}
