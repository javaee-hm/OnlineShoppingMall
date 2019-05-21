package Controllers;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Bean.BeanOrder;
import Impl.OrderServiceImpl;

import exception.BooksNotFoundException;



@Controller
public class ShowOrderController {
	OrderServiceImpl orderService;
	

    @RequestMapping("/ShowOrder")
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	ApplicationContext applicationContext=(ApplicationContext)request.getSession().getServletContext().getAttribute("ctx");
        orderService=(OrderServiceImpl)applicationContext.getBean("OrderServiceImpl");
		Collection<BeanOrder> orders;
		try {
			orders = orderService.getOrders((String)request.getSession().getAttribute("userid"),true);		
			request.setAttribute("orders", orders);
			request.getRequestDispatcher("/jsp/orderlist.jsp").forward(request, response);
		} catch (BooksNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	}
}
