package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import Impl.UserServiceImpl;
import listeners.ContextListener;

/**
 * Servlet Filter implementation class MainFilter
 */
public class MainFilter implements Filter {
	UserServiceImpl userService;
    /**
     * Default constructor. 
     */
    public MainFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		// pass the request along the filter chain
		if(req.getRequestURI().endsWith("exit")) {
			req.getSession().invalidate();
			req.getRequestDispatcher("index.html").forward(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub

		try {
			ApplicationContext applicationContext=(ApplicationContext)fConfig.getServletContext().getAttribute("ctx");
			userService=(UserServiceImpl)applicationContext.getBean("UserServiceImpl");	
		}catch(NullPointerException ex) {
		}
	}

}
