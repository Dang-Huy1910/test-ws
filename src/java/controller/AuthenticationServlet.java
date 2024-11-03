/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;


/**
 *
 * @author trung
 */
public abstract class AuthenticationServlet extends HttpServlet {

    private Account getAuthentication(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("account");
        return account;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account user = getAuthentication(req);
        if (user != null) {
            doPost(req, resp, user);
        } else {
            //http://localhost:8080/Workshop/AddAccount
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                    + req.getContextPath();

            resp.sendRedirect(url + "/login");
        }
    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, Account user)
            throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account user = getAuthentication(req);
        if (user != null) {
            doGet(req, resp, user);
        } else {
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                    + req.getContextPath();

            resp.sendRedirect(url + "/login");
        }

    }

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, Account user)
            throws ServletException, IOException;

}
