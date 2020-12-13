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
public class DataBaseFile {
    public void create(File file){
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        Date date = new Date();
        String sql = "Insert Into File(Directory, FileName)"
                    + "Values (\"" + file.getDirectory() + "\", \""+ file.getFileName() + "\");");

        stmt.executeQuery(sql);
    }
    
    public void delete(int id){
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        Date date = new Date();
        String sql = "Delete from File "
                    + "Where idFile="+ id +";";

        stmt.executeQuery(sql);
    }
    
    public void update(File file){
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();
        Date date = new Date();
        String sql = "Update File "
                    + "Set Directory=\""+ file.getDirectory()"\", FileName=\""+ file.getFileName()+"\" "
                    + "Where idFile="+ file.getId() + ";";

        stmt.executeQuery(sql);
    }
}
