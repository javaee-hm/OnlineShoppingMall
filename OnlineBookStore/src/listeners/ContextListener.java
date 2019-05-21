/*
 * 
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc. Use is
 * subject to license terms.
 *  
 */

package listeners;



import javax.servlet.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.Counter;

public final class ContextListener implements ServletContextListener {
    private ServletContext context = null;
   
	public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("application.xml");
        context.setAttribute("ctx", applicationContext);
 
        Counter counter = new Counter();
        context.setAttribute("hitCounter", counter);
        counter = new Counter();
        context.setAttribute("orderCounter", counter);
    }

    public void contextDestroyed(ServletContextEvent event) {
        context = event.getServletContext();

        context.removeAttribute("ctx");
        context.removeAttribute("hitCounter");
        context.removeAttribute("orderCounter");
    }
}
