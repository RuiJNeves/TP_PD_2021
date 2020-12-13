/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import Features.*;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ruizi
 */
public class DatabaseSentMessageUser {
    
      public static void insert(Message m, int snd, int rcv) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "Insert Into SentMessageUser(idMessage, idSender, idReceiver) "
                + "Values (\""+m.getId()+"\", \""+ snd + "\","+rcv+ ");";
        stmt.executeQuery(sql);
    }
}
