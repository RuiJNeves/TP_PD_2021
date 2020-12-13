/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import Features.*;
import java.net.InetAddress;
import java.sql.*;

/**
 *
 * @author ruizi
 */
public class DatabaseSentFileChannel {
    
    public static void insert(int userSender, int c, File file,InetAddress bd ) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon(bd).getConnection().createStatement();
        String sql = "Insert Into SentMessageChannel(idFile, idChannel , User_idUser) " 
                + "Values(" + file.getId() + ", " + c + ", " + userSender + ");";
        

        stmt.executeQuery(sql);
    }
}
