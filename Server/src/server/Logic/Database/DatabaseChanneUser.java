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
public class DatabaseChanneUser {
    
      public static void insert(Channel c, User u) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "Insert Into Channel_has_User(idChannel, idUser)"
                + "Values (\""+c.getId()+"\", \""+ u.getId() + ");";
        stmt.executeQuery(sql);
    }
    
    public static void delete(Channel c, User u) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "DELETE FROM Channel_has_User WHERE idChannel = " + c.getId() + " AND idUser = " + u.getId() + ";";
        stmt.executeQuery(sql);
    }
}
