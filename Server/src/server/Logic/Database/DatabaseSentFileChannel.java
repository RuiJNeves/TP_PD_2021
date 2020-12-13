/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import Features.*;
import java.sql.*;

/**
 *
 * @author ruizi
 */
public class DatabaseSentFileChannel {
    
    public static void insert(User userSender, Channel c, File file ) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "Insert Into SentMessageChannel(idFile, idChannel , User_idUser) " 
                + "Values(" + file.getId() + ", " + c.getId() + ", " + userSender.getId() + ");";
        

        stmt.executeQuery(sql);
    }
}
