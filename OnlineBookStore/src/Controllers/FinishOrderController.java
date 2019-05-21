package Controllers;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;

import Impl.OrderServiceImpl;
import exception.BooksNotFoundException;


/**
 * Servlet implementation class FinishOrderController
 */
public class FinishOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderServiceImpl orderService;

    @RequestMapping("/FinishOrder")
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	ApplicationContext applicationContext=(ApplicationContext)request.getSession().getServletContext().getAttribute("ctx");
        orderService=(OrderServiceImpl)applicationContext.getBean("OrderServiceImpl");

		int orderid=Integer.parseInt(request.getParameter("Finish"));
		orderService.finishOrder(orderid);
		request.getRequestDispatcher("/ShowOrder").forward(request, response);     
	}

}
