/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Category;

@WebServlet("/category")
public class CategoryServlet extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            CategoryDAO categoryDAO = new CategoryDAO(req.getServletContext());

            String search = req.getParameter("search") != null ? req.getParameter("search") : "";
            int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;
            int pageSize = 3;
            int offset = (page - 1) * pageSize;

            List<Category> list = categoryDAO.listCategories(search, offset, pageSize);
            int totalCategories = categoryDAO.countCategories(search);
            int totalPages = (int) Math.ceil((double) totalCategories / pageSize);

            req.setAttribute("list", list);
            req.setAttribute("search", search);
            req.setAttribute("page", page);
            req.setAttribute("totalPages", totalPages);

            req.getRequestDispatcher("category.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
