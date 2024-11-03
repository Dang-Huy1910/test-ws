/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectDB {
    private String hostName;
    private String instance;
    private String port;
    private String dbName;
    private String user;
    private String pass;
    
    public ConnectDB(){
        this.hostName="";
        this.instance="";
        this.port="";
        this.dbName="";
        this.user="";
        this.pass="";
    }
    public ConnectDB(ServletContext sc){
        this.hostName=sc.getInitParameter("hostAddress");
        this.instance=sc.getInitParameter("instance");
        this.port=sc.getInitParameter("dbPort");
        this.dbName=sc.getInitParameter("dbName");
        this.user=sc.getInitParameter("userName");
        this.pass=sc.getInitParameter("userPass");
    }
    public String URLString(){
        String fm = "jdbc:sqlserver://%s\\%s:%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true";
        return String.format(fm, this.hostName,this.instance.trim(),this.port,this.dbName,this.user,this.pass);
    }
    public Connection getConnection() throws SQLException, ClassNotFoundException{	
        String  connectionURL = URLString();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection c = DriverManager.getConnection(connectionURL);
            return c;
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;    
    } 
    
}
