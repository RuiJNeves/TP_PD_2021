/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import java.net.InetAddress;
import java.sql.*;
/**
 *
 * @author ruizi
 */
public class DBConnection {
    
    private static DBConnection con = null;
    
    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static String DB_LINK;
    private static final String USER = "java";
    private static final String PASS = "javapassword123";
    
    final private Connection CONN;
    
    private DBConnection(InetAddress bd) throws SQLException, ClassNotFoundException{
        Class.forName(DRIVER);
        DB_LINK = "jdbc:mariadb://"+ bd.toString() +"/pd_20-21";
        CONN = DriverManager.getConnection(DB_LINK, USER, PASS);
    }
    
    static public DBConnection getCon(InetAddress bd) throws SQLException, ClassNotFoundException{
        if(con == null)
            con = new DBConnection(bd);
        
        return con;
    }
    
    public Connection getConnection(){
        return CONN;
    }

            
}
