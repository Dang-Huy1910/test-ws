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

@WebServlet("/updateCategory")
public class UpdateCategoryServlet extends AuthenticationServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            int typeId = Integer.parseInt(req.getParameter("id"));
            CategoryDAO categoryDAO = new CategoryDAO(req.getServletContext());
            Category category = categoryDAO.getObjectById(typeId+"");
            req.setAttribute("category", category);
            req.getRequestDispatcher("updateCategory.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException ex) {
            req.getRequestDispatcher("category.jsp").forward(req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("error", "Error loading database driver.");
            req.getRequestDispatcher("updateCategory.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            CategoryDAO categoryDAO = new CategoryDAO(req.getServletContext());
            // Nhận thông tin từ biểu mẫu
            int typeId = Integer.parseInt(req.getParameter("typeId"));
            String categoryName = req.getParameter("categoryName");
            String memo = req.getParameter("memo");
            Category c = categoryDAO.getObjectById(typeId+"");
            Category c2 = categoryDAO.getObjectByName(categoryName);
            // Kiểm tra xem categoryName có tồn tại không
            if(c2!=null){
                if(!c.getCategoryName().equals(c2.getCategoryName())){
                    if ( c!= null) {
                        req.setAttribute("error2", "Category Name already exists!");
                        req.setAttribute("category", new Category(typeId, categoryName, memo));
                        req.getRequestDispatcher("updateCategory.jsp").forward(req, resp);
                        return;
                    }
                }
            }

            // Cập nhật đối tượng Category
            Category category = new Category(typeId, categoryName, memo);
            categoryDAO.updateCategory(category);
            resp.sendRedirect("category"); // Chuyển hướng đến trang danh sách category

        } catch (SQLException ex) {
            Logger.getLogger(UpdateCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("error", "Database error. Please try again.");
            req.getRequestDispatcher("updateCategory.jsp").forward(req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("error", "Error loading database driver.");
            req.getRequestDispatcher("updateCategory.jsp").forward(req, resp);
        }
    }
}
