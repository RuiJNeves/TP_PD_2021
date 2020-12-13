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
public class DataBaseMessage {
    

    public void create(Message txt) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        Date date = new Date();
        String sql = "Insert Into Message(Text, Date)"
                    + "Values (\"" + txt.getMessage() + "\", "+ date + ");";

        stmt.executeQuery(sql);
    }
    
    public void delete(int id) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "Delete from Message "
                    + "Where idMessage="+ id +";";

        stmt.executeQuery(sql);
    }
    
    public void update(Message txt) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "Update Message "
                    + "Set Text=\""+ txt.getMessage()+"\", "
                    + "Where idMessage="+ txt.getId() + ";";

        stmt.executeQuery(sql);
    }
}
