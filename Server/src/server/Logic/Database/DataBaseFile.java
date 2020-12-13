/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import java.sql.*;
import Features.*;
import java.net.InetAddress;
/**
 *
 * @author Hugo
 */
public class DataBaseFile {
    public static void insert(File file, InetAddress bd) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon(bd).getConnection().createStatement();
        String sql = "Insert Into File(Directory, FileName)"
                    + "Values (\"" + file.getDir() + "\", \""+ file.getFile() + "\");";

        stmt.executeQuery(sql);
    }
}
