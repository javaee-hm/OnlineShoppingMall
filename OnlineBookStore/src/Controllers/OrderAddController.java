package Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Impl.OrderServiceImpl;
import cart.ShoppingCart;


@Controller
public class OrderAddController {
	OrderServiceImpl orderService;

    @RequestMapping("/OrderAdd")
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	ApplicationContext applicationContext=(ApplicationContext)request.getSession().getServletContext().getAttribute("ctx");
		orderService=(OrderServiceImpl)applicationContext.getBean("OrderServiceImpl");
		String userid=(String)(request.getSession().getAttribute("userid"));
		ShoppingCart cart=(ShoppingCart)request.getSession().getAttribute("cart");
		String address=(String)(request.getSession().getAttribute("address"));
		try {
			orderService.AddOrder(userid,cart,address);
			cart.clear();
			request.getSession().setAttribute("cart", cart);
			request.getRequestDispatcher("/ShowOrder").forward(request, response);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.getSession().setAttribute("errmsg", e.getMessage());
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/showcart");
		}
	}
}
