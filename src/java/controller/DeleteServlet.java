/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.AccountDAO;
import dao.CategoryDAO;
import dao.ProductDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Category;
import model.Product;

@WebServlet("/delete")
public class DeleteServlet extends AuthenticationServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        System.out.println("delete");
        String action = req.getParameter("action");
        String id = req.getParameter("id");
        if(action.equals("account")){
            System.out.println("delete account 1");
            try {
                AccountDAO accountDAO = new AccountDAO(req.getServletContext());
                Account account = new Account();
                account.setAccount(id);
                System.out.println("delete account 1");
                accountDAO.deleteRec(account);
                System.out.println("delete account 2");
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (SQLException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(action.equals("product")){
            try {
                ProductDAO productDAO = new ProductDAO(req.getServletContext());
                Product product = new Product();
                product.setProductId(id);
                productDAO.deleteRec(product);
                System.out.println("delete account 2");
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (SQLException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(action.equals("category")){
            try {
                CategoryDAO categoryDAO = new CategoryDAO(req.getServletContext());
                Category category = new Category();
                category.setTypeId(Integer.parseInt(id));
                categoryDAO.deleteRec(category);
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (SQLException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
