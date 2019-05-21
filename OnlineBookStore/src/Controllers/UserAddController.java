package Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Impl.UserServiceImpl;

@Controller
public class UserAddController {
	UserServiceImpl userService;
	@RequestMapping("/UserAdd")
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
			// TODO Auto-generated method stub
			ApplicationContext applicationContext=(ApplicationContext)request.getSession().getServletContext().getAttribute("ctx");
	        userService=(UserServiceImpl)applicationContext.getBean("UserServiceImpl");
	        
	        String userid=(String)request.getParameter("userid");
			String pwd=(String)request.getParameter("pwd");
			String username=(String)request.getParameter("username");
			try {
				userService.useradd(userid, pwd, username);
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO: handle exception
				request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
			}
			
	}
}
