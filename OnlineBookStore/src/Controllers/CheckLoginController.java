package Controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Impl.UserServiceImpl;

@Controller
public class CheckLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserServiceImpl userService;
	@RequestMapping("/checklogin")
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			ApplicationContext applicationContext=(ApplicationContext)request.getSession().getServletContext().getAttribute("ctx");
	        userService=(UserServiceImpl)applicationContext.getBean("UserServiceImpl");
	        String userid=(String)request.getParameter("userid");
			String pwd=(String)request.getParameter("pwd");
			int identity=Integer.parseInt(request.getParameter("identity"));
			boolean ismanager=true;
			if(identity==0)ismanager=false;
			try {
				userService.checkuser(userid, pwd,ismanager);
				request.getSession().setAttribute("userid", userid);
				response.sendRedirect("index.html");			
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				request.getSession().setAttribute("errmsg", e.getMessage());
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
				return;
			}			
	}
}
