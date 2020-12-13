/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import java.sql.*;
import Features.*;
import java.net.InetAddress;
import java.time.LocalDate;
/**
 *
 * @author Hugo
 */
public class DataBaseMessage {
    

    public static void insert(Message txt,InetAddress bd) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon(bd).getConnection().createStatement();
        LocalDate date = LocalDate.now();
        String sql = "Insert Into Message(Text, Date)"
                    + "Values (\"" + txt.getMessage() + "\", "+ date + ");";

        stmt.executeQuery(sql);
    }
}
