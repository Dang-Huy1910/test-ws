/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ProductDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Product;

@WebServlet("/product")
public class ProductServlet extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            String search = req.getParameter("search"); // Lấy từ khóa tìm kiếm
            int page = Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "1"); // Trang hiện tại
            int pageSize = 3; // Số sản phẩm trên mỗi trang

            ProductDAO productDAO = new ProductDAO(req.getServletContext());

            // Lấy tổng số sản phẩm và tính tổng số trang
            int totalProducts = productDAO.countProducts(search);
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            // Lấy danh sách sản phẩm theo từ khóa tìm kiếm và trang hiện tại
            List<Product> list = productDAO.searchAndPaginate(search, page, pageSize);

            req.setAttribute("productList", list);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);

            req.getRequestDispatcher("product.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
