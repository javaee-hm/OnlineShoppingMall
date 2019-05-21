package Controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;

import Bean.BeanBook;
import Impl.BookServiceImpl;
import exception.BookNotFoundException;
/**
 * Servlet implementation class BookDetailsController
 */
public class BookDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private BookServiceImpl bookService;

    @RequestMapping("/bookdetails")
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	ApplicationContext applicationContext=(ApplicationContext)request.getSession().getServletContext().getAttribute("ctx");
        bookService=(BookServiceImpl)applicationContext.getBean("BookServiceImpl");
        int bookId = 0;
        if (request.getParameter("bookId") != null) {
            try {
            	bookId=Integer.parseInt(request.getParameter("bookId"));
                BeanBook bd = bookService.getBookDetails(bookId);
                request.setAttribute("bd",bd);
                request.getRequestDispatcher("/jsp/bookdetail.jsp").forward(request, response);
                return;
            } catch (BookNotFoundException ex) {
                throw new ServletException(ex);
            }

        }
    }
}
