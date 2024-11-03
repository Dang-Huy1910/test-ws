/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.AccountDAO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;

import model.Account;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String userName = req.getParameter("username");
            String passWord = req.getParameter("password");
            String warningLogin;
            String url;
            ServletContext servletContext = req.getServletContext();
            AccountDAO accountDAO = new AccountDAO(servletContext);
            Account account = accountDAO.getAccountLogin(userName, passWord);
            System.err.println("account:" + account);
            if (account == null) {
                url = "login.jsp";
                warningLogin = "username or password incorrect";
                req.setAttribute("warningLogin", warningLogin);
                req.getRequestDispatcher(url).forward(req, resp);
            } else {
                
                Cookie c_username = new Cookie("username", userName);
                c_username.setMaxAge(3600 * 24 * 7);

                Cookie c_password = new Cookie("password", passWord);
                c_password.setMaxAge(3600 * 24 * 7);
                String remember = req.getParameter("remember");
                if (remember != null) {
                    Cookie c_username_rm = new Cookie("usernameRM", userName);
                    c_username_rm.setMaxAge(3600 * 24 * 7);

                    Cookie c_password_rm = new Cookie("passwordRM", passWord);
                    c_password_rm.setMaxAge(3600 * 24 * 7);
                    resp.addCookie(c_username_rm);
                    resp.addCookie(c_password_rm);
                }else {
                    Cookie c_username_rm = new Cookie("usernameRM", "");
                    c_username_rm.setMaxAge(-1);

                    Cookie c_password_rm = new Cookie("passwordRM", "");
                    c_password_rm.setMaxAge(-1);

                    resp.addCookie(c_username_rm);
                    resp.addCookie(c_password_rm);
                }

                url = "home.jsp";
                HttpSession session = req.getSession();
                session.setAttribute("account", account);
                resp.sendRedirect("account");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

}
