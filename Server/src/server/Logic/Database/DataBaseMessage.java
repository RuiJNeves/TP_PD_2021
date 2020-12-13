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
public class DataBaseMessage {
    

    public void create(Message txt){
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        Date date = new Date();
        String sql = "Insert Into Message(Text, Date)"
                    + "Values (\"" + txt.getMessage() + "\", "+ date + ");");

        stmt.executeQuery(sql);
    }
    
    public void delete(int id){
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        Date date = new Date();
        String sql = "Delete from Message "
                    + "Where idMessage="+ id +";";

        stmt.executeQuery(sql);
    }
    
    public void update(Message txt){
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        Date date = new Date();
        String sql = "Update Message "
                    + "Set Text=\""+ txt.getMessage()"\", Date="+ date+" "
                    + "Where idMessage="+ txt.getId() + ";";

        stmt.executeQuery(sql);
    }
}
