/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import java.sql.*;
import Features.*;
import java.util.ArrayList;

/**
 *
 * @author Hugo
 */
public class DataBaseSentMessageChannel {
    public static void inset (User user, Channel channel, Message txt) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "Insert Into SentMessageChannel(idUser, idChannel, idMessage)"
                    + "Values (" + user.getId() + ", "+ channel.getId() +", "+ txt.getId() + ");";

        stmt.executeQuery(sql);
    }
    
        public static  ArrayList<String> getMsgs(int id, int n) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "SELECT TOP " + n + " Message.Text, User.Name FROM Message "
                + "INNER JOIN MessageSentChannel ON Message.idMessage = MessageSentChannel.idMessage "
                + "INNER JOIN User ON MessageSentChannel.idUser = User.idUser"
                + "WHERE MessageSentChannel.idChannel = " + id
                + " ORDER BYMessageSentChannel.idSentMessageChannel DESC;";

        ResultSet r = stmt.executeQuery(sql);
        ArrayList<String> ret = new ArrayList<>(n);
        while(r.next()){
            ret.add(r.getString(2) + ": " + r.getString(1));
        }
        return ret;
    }
}
