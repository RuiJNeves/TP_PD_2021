/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic.Database;

import Features.Channel;
import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author ruizi
 */
public class DatabaseChannel {
    
    public static void insert(Channel c,InetAddress bd) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon(bd).getConnection().createStatement();
        String sql = "Insert Into Channel(Name, Password, Descricao, idCreator)"
                + "Values (\""+c.getNome()+"\", \""+ c.getPassword() + "\",\""+c.getDescricao()+"\", " + c.getIdCreator() + ");";
        stmt.executeQuery(sql);
    }
    
    public static void delete(Channel c,InetAddress bd) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon(bd).getConnection().createStatement();
        String sql = "DELETE FROM Channel WHERE idChannel = " + c.getId() + ";";
        stmt.executeQuery(sql);
    }
    
    public static void update(Channel c,InetAddress bd) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon(bd).getConnection().createStatement();
        String sql = "UPDATE Channel "
                + "SET Name = \""+c.getNome()+"\", Password = \""+ c.getPassword() + "\", Descricao = \""+c.getDescricao()+"\" "
                + "WHERE idChannel = " + c.getId() + ";";
        stmt.executeQuery(sql);
    }
    
    public static int getChannelByName(String s,InetAddress bd) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon(bd).getConnection().createStatement();
        String sql = "SELECT TOP 1 * FROM Channel WHERE Name = \"" + s + "\";";
        ResultSet r = stmt.executeQuery(sql);
        int ret =  Integer.parseInt(r.getArray(0).toString());
        r.close();
        return ret;
    }

    public static ArrayList<String> getInfo(InetAddress bd) throws SQLException, ClassNotFoundException {
        Statement stmt = DBConnection.getCon(bd).getConnection().createStatement();
        String sql = "SELECT* FROM Channel;";
        ResultSet r = stmt.executeQuery(sql);
        ArrayList<String> ret = new ArrayList<>();
        while(r.next()){
           
            String s = "id: "+ r.getInt(1)+" Nome: " + r.getString(2) + "Descricao: ";
            r.getString(3);
            s = s + r.getString(4);
            ret.add(s);
        }
        r.close();
        return ret;
    }

    public static ArrayList<String> getStats(int id,InetAddress bd) throws SQLException, ClassNotFoundException {
        Statement stmt = DBConnection.getCon(bd).getConnection().createStatement();
        String sqlUsers = "SELECT COUNT(*) FROM Channel_has_User WHERE idChannel = " + id + ";";
        ResultSet r = stmt.executeQuery(sqlUsers);
        int users = r.getInt(1);
        r.close();
        String sqlFiles = "SELECT COUNT(*) FROM SentFileChannel WHERE idChannel = " + id + ";";
        r = stmt.executeQuery(sqlFiles);
        int files = r.getInt(1);
        r.close();
        String sqlMsgs = "SELECT COUNT(*) FROM SentMessageChannel WHERE idChannel = " + id + ";";
        r = stmt.executeQuery(sqlFiles);
        int msgs = r.getInt(1);
        ArrayList<String> ret = new ArrayList<>(3);
        ret.add(("Utilizadores: " + users));
        ret.add(("Mensagens: " + msgs));
        ret.add(("Ficheiros: " + files));
        r.close();
        return ret;
    }

    public static int getChannelWithPass(String channel, String pass, InetAddress bd) throws SQLException, ClassNotFoundException {
        Statement stmt = DBConnection.getCon(bd).getConnection().createStatement();
        String sql = "SELECT id FROM Channel WHERE  Name= \"" + channel + "\" and Password=\""+ pass+ "\";";
        ResultSet r = stmt.executeQuery(sql);
        
        while(r.next())
            return r.getInt(1);
        
        return -1;
    }
    
}
