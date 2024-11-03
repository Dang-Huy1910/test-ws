/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.AccountDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

@WebServlet("/updateAccount")
public class UpdateServlet extends AuthenticationServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String isUseParam = req.getParameter("isUse");
            boolean isUse = (isUseParam != null);
            AccountDAO accountDAO = new AccountDAO(req.getServletContext());
            String account = req.getParameter("account");
            String pass = req.getParameter("pass");
            String lastName = req.getParameter("lastName");
            String firstName = req.getParameter("firstName");
            String birthdayStr = req.getParameter("birthday");
            java.sql.Date birthday = null;
            if (birthdayStr != null) {
                birthday = java.sql.Date.valueOf(birthdayStr);
            }
            boolean gender = "Male".equals(req.getParameter("gender"));
            String phone = req.getParameter("phone");
            if (!phone.matches("^(03|05|07|08|09)\\d+$")) {
                req.setAttribute("error","Phone number must start with 03, 05, 07, 08, or 09 and contain only digits.");
                req.getRequestDispatcher("addAccount.jsp").forward(req, resp);
                return;
            }
            int roleInSystem = Integer.parseInt(req.getParameter("roleInSystem"));
            Account account1 = new Account(account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem);
            accountDAO.updateRec(account1);
            resp.sendRedirect("account");
        } catch (SQLException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            AccountDAO accountDAO = new AccountDAO(req.getServletContext());
            Account account = accountDAO.getObjectById(id);
            req.setAttribute("account", account);
            req.getRequestDispatcher("updateAccount.jsp").forward(req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
