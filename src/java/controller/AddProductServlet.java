/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import model.Account;
import model.Category;
import model.Product;
import java.nio.file.Path;

@WebServlet("/addProduct")
@MultipartConfig(
        location = "C:\\Users\\trung\\Documents\\NetBeansProjects\\Workshop\\img\\product",
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class AddProductServlet extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            ProductDAO productDAO = new ProductDAO(req.getServletContext());
            // Receive information from the form
            String productId = req.getParameter("productId");
            if (productDAO.getObjectById(productId) != null) {
                req.setAttribute("error", "Product ID already exists!");
                setCategories(req);
                req.getRequestDispatcher("addProduct.jsp").forward(req, resp);
                return;
            }
            // Get the uploaded file
            Part imagePart = req.getPart("image");
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString(); // Safe extraction of file name
            System.out.println("Received file: " + fileName);

            String imagePath = "/img/product/" + fileName;
            Path imagePathOnDisk = Paths.get(req.getServletContext().getRealPath("") + imagePath);
            System.out.println("Image will be saved to: " + imagePathOnDisk.toString());
            Files.createDirectories(imagePathOnDisk.getParent()); // Create directories if they don't exist
            imagePart.write(imagePathOnDisk.toString()); // Save the file

            String productName = req.getParameter("productName");
            System.out.println("Name: "+productName);
            String brief = req.getParameter("brief");
            String birthdayStr = req.getParameter("postedDate");
            java.sql.Date postedDate = birthdayStr != null ? java.sql.Date.valueOf(birthdayStr) : null;
            int typeId = Integer.parseInt(req.getParameter("category"));
            String unit = req.getParameter("unit");
            int price = Integer.parseInt(req.getParameter("price"));
            int discount = Integer.parseInt(req.getParameter("discount"));

            if (price < 0 || discount < 0) {
                req.setAttribute("error", "Price or discount can't be less than 0");
                setCategories(req);
                req.getRequestDispatcher("addProduct.jsp").forward(req, resp);
                return;
            }

            // Create a Product object
            Product product = new Product();
            product.setProductId(productId);
            product.setProductName(productName);
            product.setBrief(brief);
            product.setPostedDate(postedDate);
            product.setType(new Category(typeId, "", ""));
            product.setUnit(unit);
            product.setPrice(price);
            product.setDiscount(discount);
            product.setProductImage(imagePath); // Assuming you have an imagePath field in your Product class

            productDAO.addProduct(product, user.getAccount());
            resp.sendRedirect("product");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setCategories(HttpServletRequest req) throws SQLException {
        try {
            CategoryDAO categoryDAO = new CategoryDAO(req.getServletContext());
            List<Category> list = categoryDAO.listAll();
            req.setAttribute("cate", list);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            CategoryDAO categoryDAO = new CategoryDAO(req.getServletContext());
            List<Category> list = categoryDAO.listAll();
            req.setAttribute("cate", list);
            req.getRequestDispatcher("addProduct.jsp").forward(req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
