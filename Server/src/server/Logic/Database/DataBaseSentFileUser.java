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
public class DataBaseSentFileUser {
    public void create(User userSender, User userReceiver , File file){
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        Date date = new Date();
        String sql = "Insert Into SentMessageChannel(File_idFile, idSender , idReceiver)"
                    + "Values (" + file.getId() + ", "+ userSender.getId() +", "+ userReceiver.getId() + ");");

        stmt.executeQuery(sql);
    }
}