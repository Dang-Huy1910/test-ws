/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Category;
import model.Product;
import utilities.ConnectDB;

public class ProductDAO implements Accessible<Product> {

    private ServletContext sc;
    private Connection con;

    public ProductDAO() throws SQLException, ClassNotFoundException {
    }

    public ProductDAO(ServletContext sc) throws SQLException, ClassNotFoundException {
        this.sc = sc;
        ConnectDB connectDB = new ConnectDB(sc);
        con = connectDB.getConnection();
    }

    private Connection getConnect(ServletContext sc) throws SQLException, ClassNotFoundException {
        Connection conn = null;

        ConnectDB connectDB = new ConnectDB(sc);
        return connectDB.getConnection();
    }

    @Override
    public int insertRec(Product obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int addProduct(Product product, String account) {
        int result = 0;
        String sql = "INSERT INTO [dbo].[products] ([productId], [productName], [productImage], [brief], [postedDate], [typeId], [account], [unit], [price], [discount]) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getProductImage());
            stmt.setString(4, product.getBrief());
            stmt.setDate(5, new java.sql.Date(product.getPostedDate().getTime())); // Chuyển đổi java.util.Date sang java.sql.Date
            stmt.setInt(6, product.getType().getTypeId()); // Giả sử bạn đã có phương thức getId() trong lớp Category
            stmt.setString(7, account); // Thêm trường tài khoản nếu cần
            stmt.setString(8, product.getUnit());
            stmt.setInt(9, product.getPrice());
            stmt.setInt(10, product.getDiscount());

            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateRec(Product obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleteRec(Product obj) {
        int user = 0;
        try {
            String sql = "DELETE FROM [dbo].[products] WHERE [productId] = ?";

            PreparedStatement preparedStatement = con.prepareCall(sql);
            preparedStatement.setString(1, obj.getProductId());
            user = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public Product getObjectById(String id) {
        Product product = null;
        try {
            String sql = "SELECT [productId], [productName], [productImage], [brief], [postedDate], [typeId], [account], [unit], [price], [discount] FROM [ProductIntro].[dbo].[products] WHERE [productId] = ?";

            PreparedStatement preparedStatement = con.prepareCall(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String productId = resultSet.getString("productId");
                String productName = resultSet.getString("productName");
                String productImage = resultSet.getString("productImage");
                String brief = resultSet.getString("brief");
                java.sql.Date postedDate = resultSet.getDate("postedDate");
                String unit = resultSet.getString("unit");
                int price = resultSet.getInt("price");
                int discount = resultSet.getInt("discount");
                int typeId = resultSet.getInt("typeId");
                CategoryDAO categoryDAO = new CategoryDAO(sc);
                Category category = categoryDAO.getObjectById(typeId + "");
                product = new Product(productId, productName, productImage, brief, postedDate, category, unit, price, discount);

            }
        } catch (SQLException e) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return product;
    }

    @Override
    public List<Product> listAll() {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String sql = "SELECT [productId], [productName], [productImage], [brief], [postedDate], [typeId], [account], [unit], [price], [discount] FROM [ProductIntro].[dbo].[products]";

            PreparedStatement preparedStatement = con.prepareCall(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String productId = resultSet.getString("productId");
                String productName = resultSet.getString("productName");
                String productImage = resultSet.getString("productImage");
                String brief = resultSet.getString("brief");
                java.sql.Date postedDate = resultSet.getDate("postedDate");
                String unit = resultSet.getString("unit");
                int price = resultSet.getInt("price");
                int discount = resultSet.getInt("discount");
                int typeId = resultSet.getInt("typeId");
                CategoryDAO categoryDAO = new CategoryDAO(sc);
                Category category = categoryDAO.getObjectById(typeId + "");
                Product product = new Product(productId, productName, productImage, brief, postedDate, category, unit, price, discount);
                list.add(product);

            }
        } catch (SQLException e) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int countProducts(String search) throws SQLException {
        String sql = "SELECT COUNT(*) FROM products \n";
        if (search != null && !search.trim().isEmpty()) {
            sql += " WHERE productName LIKE ?";
        }
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            if (search != null && !search.trim().isEmpty()) {
                stmt.setString(1, "%" + search + "%");
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Product> searchAndPaginate(String search, int page, int pageSize) throws SQLException {
        List<Product> productList = new ArrayList<>();
        String sql = "  select * from [dbo].[products]\n";
        if (search != null && !search.trim().isEmpty()) {
            sql += " WHERE [productName] LIKE ?\n";
        }
        sql += "order by [productId]\n"
            + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY\n";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            int paramIndex = 1;
            if (search != null && !search.trim().isEmpty()) {
                preparedStatement.setString(paramIndex++, "%" + search + "%");
            }
            preparedStatement.setInt(paramIndex++, (page - 1) * pageSize);
            preparedStatement.setInt(paramIndex,  pageSize);

            ResultSet resultSet = preparedStatement.executeQuery();;
            while (resultSet.next()) {
                String productId = resultSet.getString("productId");
                String productName = resultSet.getString("productName");
                String productImage = resultSet.getString("productImage");
                String brief = resultSet.getString("brief");
                java.sql.Date postedDate = resultSet.getDate("postedDate");
                String unit = resultSet.getString("unit");
                int price = resultSet.getInt("price");
                int discount = resultSet.getInt("discount");
                int typeId = resultSet.getInt("typeId");
                CategoryDAO categoryDAO = new CategoryDAO(sc);
                Category category = categoryDAO.getObjectById(typeId + "");
                Product product = new Product(productId, productName, productImage, brief, postedDate, category, unit, price, discount);
                productList.add(product);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }
    public void updateProduct(Product product, Account user) throws SQLException {
    String sql = "UPDATE products SET "
               + "[productName] = ?, \n"
               + "[productImage] = ?,\n "
               + "brief = ?, \n"
               + "[postedDate] = ?, \n"
               + "[typeId] = ?, \n"
               + "unit = ?, \n"
               + "price = ?, \n"
               + "discount = ?, \n"
                + "[account] = ? \n"
               + "WHERE [productId] = ?";

    try (Connection conn = con;
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, product.getProductName());
        pstmt.setString(2, product.getProductImage());
        pstmt.setString(3, product.getBrief());
        pstmt.setDate(4, product.getPostedDate());
        pstmt.setInt(5, product.getType().getTypeId()); // Assuming Category has a getTypeId method
        pstmt.setString(6, product.getUnit());
        pstmt.setInt(7, product.getPrice());
        pstmt.setInt(8, product.getDiscount());
        pstmt.setString(9, user.getAccount());
        pstmt.setString(10, product.getProductId()); // Product ID to identify which record to update

        pstmt.executeUpdate();
    }
}

}
