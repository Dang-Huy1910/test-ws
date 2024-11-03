/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Account;
import model.Category;
import model.Product;
import java.nio.file.Path;
import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@WebServlet("/updateProduct")
@MultipartConfig(location = "C:\\Users\\trung\\Documents\\NetBeansProjects\\Workshop\\img\\product")
public class updateProductServlet extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            ProductDAO productDAO = new ProductDAO(req.getServletContext());
            String productId = req.getParameter("productId");
            Product existingProduct = productDAO.getObjectById(productId);

            if (existingProduct == null) {
                req.setAttribute("error", "Product not found!");
                req.getRequestDispatcher("updateProduct.jsp").forward(req, resp);
                return;
            }
            String imagePath = productDAO.getObjectById(productId).getProductImage();
            // Check for the new image upload
            Part imagePart = req.getPart("image");
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            if (fileName != null && !fileName.isEmpty()) {
                
                System.out.println("Received file: " + fileName);

                imagePath = "/img/product/" + fileName;
                Path imagePathOnDisk = Paths.get(req.getServletContext().getRealPath("") + imagePath);
                if (imagePart != null && imagePart.getSize() > 0) {
                    Files.createDirectories(imagePathOnDisk.getParent());
                    imagePart.write(imagePathOnDisk.toString());
                } else {
                    fileName = existingProduct.getProductImage(); // Keep the old image if no new one is uploaded
                }
            }

            // Update product fields
            String productName = req.getParameter("productName");
            String brief = req.getParameter("brief");
            String postedDateStr = req.getParameter("postedDate");
            java.sql.Date postedDate = java.sql.Date.valueOf(postedDateStr);
            int typeId = Integer.parseInt(req.getParameter("category"));
            String unit = req.getParameter("unit");
            int price = Integer.parseInt(req.getParameter("price"));
            int discount = Integer.parseInt(req.getParameter("discount"));

            Product product = new Product();
            product.setProductId(productId);
            product.setProductName(productName);
            product.setBrief(brief);
            product.setPostedDate(postedDate);
            product.setType(new Category(typeId, "", ""));
            product.setUnit(unit);
            product.setPrice(price);
            product.setDiscount(discount);

            product.setProductImage(imagePath); // Set the updated image path

            productDAO.updateProduct(product, user);
            resp.sendRedirect("product");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(updateProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        String productId = req.getParameter("productId");

        if (productId != null && !productId.isEmpty()) {
            try {
                ProductDAO productDAO = new ProductDAO(req.getServletContext());
                Product product = productDAO.getObjectById(productId); // Retrieve the product by ID

                if (product != null) {
                    req.setAttribute("product", product); // Set the product as a request attribute
                    setCategories(req); // Set categories for the dropdown if needed
                    req.getRequestDispatcher("updateProduct.jsp").forward(req, resp); // Forward to the update form JSP
                } else {
                    req.setAttribute("error", "Product not found!");
                    req.getRequestDispatcher("error.jsp").forward(req, resp); // Handle product not found
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(updateProductServlet.class.getName()).log(Level.SEVERE, null, ex);
                req.setAttribute("error", "An error occurred while retrieving the product.");
                req.getRequestDispatcher("error.jsp").forward(req, resp); // Handle errors
            }
        } else {
            req.setAttribute("error", "Invalid Product ID.");
            req.getRequestDispatcher("error.jsp").forward(req, resp); // Handle invalid ID
        }
    }

    private void setCategories(HttpServletRequest req) throws SQLException {
        try {
            CategoryDAO categoryDAO = new CategoryDAO(req.getServletContext());
            List<Category> list = categoryDAO.listAll();
            req.setAttribute("cate", list);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(updateProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
