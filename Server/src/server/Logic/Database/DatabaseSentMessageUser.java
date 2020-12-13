/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import Features.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    public static ArrayList<String> getMsgs(int id, int n, int reqId) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "SELECT TOP " + n + " Message.Text, User.Name FROM Message "
                + "INNER JOIN MessageSentChannel ON Message.idMessage = MessageSentChannel.idMessage "
                + "INNER JOIN User ON MessageSentChannel.idUser = User.idUser"
                + "WHERE (MessageSentUser.idReceiver = " + id + " OR MessageSentUser.idSender = " + id + ")"
                + "AND (MessageSentUser.idReceiver = " + reqId + " OR MessageSentUser.idSender = " + reqId +")"
                + " ORDER BYMessageSentUser.idSentMessageUser DESC;";

        ResultSet r = stmt.executeQuery(sql);
        ArrayList<String> ret = new ArrayList<>(n);
        while(r.next()){
            ret.add(r.getString(2) + ": " + r.getString(1));
        }
        return ret;
    }
}
