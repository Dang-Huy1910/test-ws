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

@WebServlet("/addCategory")
public class AddCategoryServlet extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            CategoryDAO categoryDAO = new CategoryDAO(req.getServletContext());
            String categoryName = req.getParameter("categoryName");
            String memo = req.getParameter("memo");

            // Kiểm tra xem categoryName có tồn tại không
            if (categoryDAO.getObjectByName(categoryName) != null) {
                req.setAttribute("error", "Category Name already exists!");
                req.getRequestDispatcher("addCategory.jsp").forward(req, resp);
                return;
            }

            Category category = new Category();
            category.setCategoryName(categoryName);
            category.setMemo(memo);

            categoryDAO.AddCategory(category);

            resp.sendRedirect("category");

        } catch (SQLException ex) {
            Logger.getLogger(AddCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("error", "Database error. Please try again.");
            req.getRequestDispatcher("addCategory.jsp").forward(req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        req.getRequestDispatcher("addCategory.jsp").forward(req, resp);
    }
}
