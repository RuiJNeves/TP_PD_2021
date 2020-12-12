/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import java.sql.*;
/**
 *
 * @author ruizi
 */
public class DBConnection {
    
    private static DBConnection con = null;
    
    static final String DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_LINK = "jdbc:mariadb://localhost/pd_20-21";
    static final String USER = "java";
    static final String PASS = "javapassword123";
    
    final private Connection CONN;
    
    private DBConnection() throws SQLException, ClassNotFoundException{
        Class.forName(DRIVER);
        CONN = DriverManager.getConnection(DB_LINK, USER, PASS);
    }
    
    static public DBConnection getCon() throws SQLException, ClassNotFoundException{
        if(con == null)
            con = new DBConnection();
        
        return con;
    }
    
    public Connection getConnection(){
        return CONN;
    }
            
}
