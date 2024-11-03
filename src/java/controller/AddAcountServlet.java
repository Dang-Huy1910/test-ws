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
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

@WebServlet("/AddAccount")
public class AddAcountServlet extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            AccountDAO accountDAO = new AccountDAO(req.getServletContext());
            String account = req.getParameter("account");
            if(accountDAO.getObjectById(account)!=null){
                req.setAttribute("error","Accout exits!");
                req.getRequestDispatcher("addAccount.jsp").forward(req, resp);
                return;
            }
            String pass = req.getParameter("pass");
            String lastName = req.getParameter("lastName");
            String firstName = req.getParameter("firstName");
            String birthdayStr = req.getParameter("birthday");
            java.sql.Date birthday = null;
            if (birthdayStr != null) {
                birthday = java.sql.Date.valueOf(birthdayStr);  // Chuỗi phải ở định dạng yyyy-MM-dd
            }
            boolean gender = "Male".equals(req.getParameter("gender"));
            String phone = req.getParameter("phone");
            if (!phone.matches("^(03|05|07|08|09)\\d+$")) {
                req.setAttribute("error","Phone number must start with 03, 05, 07, 08, or 09 and contain only digits.");
                req.getRequestDispatcher("addAccount.jsp").forward(req, resp);
                return;
            }
            boolean isUse = "true".equals(req.getParameter("isUse"));
            int roleInSystem = Integer.parseInt(req.getParameter("roleInSystem"));
            Account account1 = new Account(account, pass, lastName, firstName, birthday, gender, phone, true, roleInSystem);
            
            accountDAO.insertRec(account1);
            resp.sendRedirect("account");
        } catch (SQLException ex) {
            Logger.getLogger(AddAcountServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddAcountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        req.getRequestDispatcher("addAccount.jsp").forward(req, resp);
    }

}
