/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import utilities.ConnectDB;

public class AccountDAO implements Accessible<Account> {

    private ServletContext sc;
    private Connection con;

    // Constructor without ServletContext
    public AccountDAO() throws SQLException, ClassNotFoundException {
        ConnectDB connectDB = new ConnectDB();
        con = connectDB.getConnection();
    }

    // Constructor with ServletContext
    public AccountDAO(ServletContext sc) throws SQLException, ClassNotFoundException {
        this.sc = sc;
        ConnectDB connectDB = new ConnectDB(sc);
        con = connectDB.getConnection();
    }

    // Private method to establish a connection
    private Connection getConnect(ServletContext sc) throws SQLException, ClassNotFoundException {
        ConnectDB connectDB = new ConnectDB(sc);
        return connectDB.getConnection();
    }

    // Get account by login credentials
    public Account getAccountLogin(String userName, String passWord) {
        Account user = null;
        try {
            String sql = "SELECT [account], [pass], [lastName], [firstName], [birthday], [gender], [phone], [isUse], [roleInSystem] " +
                         "FROM [dbo].[accounts] WHERE [account] = ? AND [pass] = ?";
            PreparedStatement preparedStatement = con.prepareCall(sql); // Truyền sql vào để thực hiện
            preparedStatement.setString(1, userName); // truyền username vào ? đầu tiên
            preparedStatement.setString(2, passWord);
            ResultSet resultSet = preparedStatement.executeQuery(); // thực hiện truy vấn
            
            if (resultSet.next()) {
                
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                java.sql.Date birthday = resultSet.getDate("birthday");
                boolean gender = resultSet.getBoolean("gender");
                String phone = resultSet.getString("phone");
                boolean isUse = resultSet.getBoolean("isUse");
                int roleInSystem = resultSet.getInt("roleInSystem");

                user = new Account(userName, passWord, lastName, firstName, birthday, gender, phone, isUse, roleInSystem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int insertRec(Account obj) {
        int result = 0;
        try {
            String sql = "INSERT INTO [dbo].[accounts] ([account], [pass], [lastName], [firstName], [birthday], [gender], [phone], [isUse], [roleInSystem]) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getAccount());
            stmt.setString(2, obj.getPass());
            stmt.setString(3, obj.getLastName());
            stmt.setString(4, obj.getFirstName());
            stmt.setDate(5, obj.getBirthday());
            stmt.setBoolean(6, obj.isGender());
            stmt.setString(7, obj.getPhone());
            stmt.setBoolean(8, obj.getIsUse());
            stmt.setInt(9, obj.getRoleInSystem());
            result = stmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int updateRec(Account obj) {
        int result = 0;
        try {
            String sql = "UPDATE [dbo].[accounts] SET [pass] = ?, [lastName] = ?, [firstName] = ?, [birthday] = ?, " +
                         "[gender] = ?, [phone] = ?, [isUse] = ?, [roleInSystem] = ? WHERE [account] = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getPass());
            stmt.setString(2, obj.getLastName());
            stmt.setString(3, obj.getFirstName());
            stmt.setDate(4, obj.getBirthday());
            stmt.setBoolean(5, obj.isGender());
            stmt.setString(6, obj.getPhone());
            stmt.setBoolean(7, obj.getIsUse());
            stmt.setInt(8, obj.getRoleInSystem());
            stmt.setString(9, obj.getAccount());
            result = stmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int deleteRec(Account obj) {
        int user = 0;
        try {
            String sql = "DELETE FROM [dbo].[accounts] WHERE [account] = ?";
            PreparedStatement preparedStatement = con.prepareCall(sql);
            preparedStatement.setString(1, obj.getAccount());
            user = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public Account getObjectById(String id) {
        Account user = null;
        try {
            String sql = "SELECT [account], [pass], [lastName], [firstName], [birthday], [gender], [phone], [isUse], [roleInSystem] " +
                         "FROM [dbo].[accounts] WHERE [account] = ?";
            PreparedStatement preparedStatement = con.prepareCall(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                java.sql.Date birthday = resultSet.getDate("birthday");
                boolean gender = resultSet.getBoolean("gender");
                String phone = resultSet.getString("phone");
                boolean isUse = resultSet.getBoolean("isUse");
                int roleInSystem = resultSet.getInt("roleInSystem");
                
                user = new Account(id, resultSet.getString("pass"), lastName, firstName, birthday, gender, phone, isUse, roleInSystem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<Account> listAll() {
        List<Account> list = new ArrayList<>();
        try {
            String sql = "SELECT [account], [pass], [lastName], [firstName], [birthday], [gender], [phone], [isUse], [roleInSystem] FROM [dbo].[accounts]";
            PreparedStatement preparedStatement = con.prepareCall(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String account = resultSet.getString("account");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                java.sql.Date birthday = resultSet.getDate("birthday");
                boolean gender = resultSet.getBoolean("gender");
                String phone = resultSet.getString("phone");
                boolean isUse = resultSet.getBoolean("isUse");
                int roleInSystem = resultSet.getInt("roleInSystem");

                Account user = new Account(account, "", lastName, firstName, birthday, gender, phone, isUse, roleInSystem);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
}
