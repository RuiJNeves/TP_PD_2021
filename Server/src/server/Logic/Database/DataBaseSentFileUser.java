/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import java.sql.*;
import Features.*;

/**
 *
 * @author Hugo
 */
public class DataBaseSentFileUser {
    public static void insert (int userSender, int userReceiver , File file) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "Insert Into SentMessageChannel(File_idFile, idSender , idReceiver)"
                    + "Values (" + file.getId() + ", "+ userSender +", "+ userReceiver + ");";

        stmt.executeQuery(sql);
    }
}
