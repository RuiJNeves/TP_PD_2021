/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

/**
 *
 * @author Hugo
 */
public class DataBaseSentMessageChannel {
    public void create(User user, Channel channel, Message txt){
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        Date date = new Date();
        String sql = "Insert Into SentMessageChannel(idUser, idChannel, idMessage)"
                    + "Values (" + user.getId() + ", "+ channel.getId() +", "+ txt.getId() + ");");

        stmt.executeQuery(sql);
    }
}
