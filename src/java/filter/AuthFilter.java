/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;

@WebFilter("/*")
public class AuthFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Account account = null;
        Object obj = session.getAttribute("account");
        if (obj != null) {
            account = (Account) obj;
        }
        String path = httpRequest.getRequestURI();

        String url = httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort()
                + httpRequest.getContextPath();
        if (path.contains(".")&&!path.contains(".jsp")) {
            chain.doFilter(request, response);
            return;
        }
        if(path.contains("/login") || path.contains("/logout")){
            chain.doFilter(request, response);
            return;
        }
        if (account != null) {
            chain.doFilter(request, response);
        } else {

            ((HttpServletResponse) response).sendRedirect(url+"/login");

        }
    }

    @Override
    public void destroy() {
        System.out.println("AuthFilter destroy.");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthFilter initialized.");
    }
    
}
