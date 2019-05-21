package Controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Bean.BeanBook;
import Impl.BookServiceImpl;
import cart.ShoppingCart;
import exception.BookNotFoundException;

/**
 * Servlet implementation class ShowCartController
 */
@Controller
public class ShowCartController extends HttpServlet {
	 BookServiceImpl bookService;

	    @RequestMapping("/showcart")
	    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    	ApplicationContext applicationContext=(ApplicationContext)request.getSession().getServletContext().getAttribute("ctx");
	    	bookService=(BookServiceImpl)applicationContext.getBean("BookServiceImpl");
	        HttpSession session = request.getSession(true);
	        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
	        String userid=(String)(request.getSession().getAttribute("userid"));
	        if(userid==null) request.getRequestDispatcher("login.html").forward(request, response);
	        else {
	        if (cart == null) {
	            cart = new ShoppingCart();
	            session.setAttribute("cart", cart);
	        }
	        int bookId=0;
	        if(request.getParameter("Remove")!=null) {
	        	bookId = Integer.parseInt(request.getParameter("Remove"));
	        	cart.remove(bookId);
	        }
	        if(request.getParameter("Add")!=null) {
	        	bookId = Integer.parseInt(request.getParameter("Add"));
	        	 try {
	                 BeanBook book = bookService.getBookDetails(bookId);
	                 cart.add(bookId, book);
	             } catch (BookNotFoundException ex) {
	                 throw new ServletException(ex);
	             }
	        }
	        request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);	
	        }    
	    }
}
