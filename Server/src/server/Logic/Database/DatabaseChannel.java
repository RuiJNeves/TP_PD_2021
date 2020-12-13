/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import Features.Channel;
import java.sql.*;
/**
 *
 * @author ruizi
 */
public class DatabaseChannel {
    
    public static void insert(Channel c) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "Insert Into Channel(Name, Password, Descricao, idCreator)"
                + "Values (\""+c.getNome()+"\", \""+ c.getPassword() + "\",\""+c.getDescricao()+"\", " + c.getIdCreator() + ");";
        stmt.executeQuery(sql);
    }
    
    public static void delete(Channel c) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "DELETE FROM Channel WHERE idChannel = " + c.getId() + ";";
        stmt.executeQuery(sql);
    }
    
    public static void update(Channel c) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "UPDATE Channel "
                + "SET Name = \""+c.getNome()+"\", Password = \""+ c.getPassword() + "\", Descricao = \""+c.getDescricao()+"\" "
                + "WHERE idChannel = " + c.getId() + ";";
        stmt.executeQuery(sql);
    }
    
}
