/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.servlet.ServletContext;
import java.util.List;
import model.Category;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utilities.ConnectDB;

public class CategoryDAO implements Accessible<Category> {

    private ServletContext sc;
    private Connection con;

    // Default constructor
    public CategoryDAO() throws SQLException, ClassNotFoundException {
    }

    // Constructor with ServletContext
    public CategoryDAO(ServletContext sc) throws SQLException, ClassNotFoundException {
        this.sc = sc;
        ConnectDB connectDB = new ConnectDB(sc);
        con = connectDB.getConnection();
    }

    // Private method to establish a connection
    private Connection getConnect(ServletContext sc) throws SQLException, ClassNotFoundException {
        ConnectDB connectDB = new ConnectDB(sc);
        return connectDB.getConnection();
    }

    @Override
    public int insertRec(Category obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int updateRec(Category obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int deleteRec(Category obj) {
        int user = 0;
        try {
            // Delete products related to the category
            String sql = "DELETE FROM [dbo].[products] WHERE [typeId] = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, obj.getTypeId());
            preparedStatement.executeUpdate();

            // Delete category
            String sql2 = "DELETE FROM [dbo].[categories] WHERE [typeId] = ?";
            preparedStatement = con.prepareStatement(sql2);
            preparedStatement.setInt(1, obj.getTypeId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public Category getObjectById(String id) {
        Category category = null;
        try {
            String sql = "SELECT [typeId], [categoryName], [memo] FROM [ProductIntro].[dbo].[categories] WHERE [typeId] = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id.trim()));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int ID = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String memo = resultSet.getString(3);
                category = new Category(ID, name, memo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public List<Category> listAll() {
        List<Category> list = new ArrayList<>();
        try {
            String sql = "SELECT [typeId], [categoryName], [memo] FROM [ProductIntro].[dbo].[categories]";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int ID = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String memo = resultSet.getString(3);
                list.add(new Category(ID, name, memo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method to add a new category
    public void addCategory(Category category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Actual implementation to add a category
    public void AddCategory(Category category) throws SQLException {
        String sql = "INSERT INTO categories (categoryName, memo) VALUES (?, ?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, category.getCategoryName());
        statement.setString(2, category.getMemo());
        statement.executeUpdate();
    }

    // Fetch a category by its name
    public Category getObjectByName(String categoryName) throws SQLException {
        String sql = "SELECT * FROM categories WHERE categoryName = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, categoryName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int typeId = resultSet.getInt("typeId");
                String memo = resultSet.getString("memo");
                return new Category(typeId, categoryName, memo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null; // If not found
    }

    // Update category details
    public void updateCategory(Category category) throws SQLException {
        String sql = "UPDATE categories SET categoryName = ?, memo = ? WHERE typeId = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, category.getCategoryName());
        statement.setString(2, category.getMemo());
        statement.setInt(3, category.getTypeId());
        statement.executeUpdate();
    }

    public List<Category> listCategories(String search, int offset, int limit) throws SQLException, ClassNotFoundException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM [ProductIntro].[dbo].[categories] WHERE categoryName LIKE ? ORDER BY categoryName OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            stmt.setInt(2, offset);
            stmt.setInt(3, limit);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int typeId = resultSet.getInt("typeId");
                String categoryName = resultSet.getString("categoryName");
                String memo = resultSet.getString("memo");
                list.add(new Category(typeId, categoryName, memo));
            }
        }
        return list;
    }

    public int countCategories(String search) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM [ProductIntro].[dbo].[categories] WHERE categoryName LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        }
        return 0;
    }

}
