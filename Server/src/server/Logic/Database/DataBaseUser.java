package server.Logic.Database;

import Features.User;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Fabio
 */
public class DataBaseUser {
    
    public static void insert(User user) throws SQLException, ClassNotFoundException{
        Statement stmt = null;

        stmt = (Statement) DBConnection.getCon().getConnection().createStatement();

        String sql = "Insert Into User(Name, Email, Password) "
                    + "Values (\""+user.getNome()+"\", \""+user.getEmail() +"\", \"" + user.getPassword() + "\");" ;

        stmt.executeQuery(sql);

    }
    
    public static void delete(int id) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = DBConnection.getCon().getConnection().createStatement();

            String sql = "Delete from User "
                    + "Where idUser = " + id + ";";

            stmt.executeQuery(sql);
    }
    
    public static void update(int id, User user) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        stmt = (Statement) DBConnection.getCon().getConnection().createStatement();

           String sql = "Update User "
                   + "Set Name=\""+ user.getNome() + "\", Email=\""+ user.getEmail()+ "\", Password=\"" + user.getPassword()+ "\" "
                   + "Where idUser="+ id + ";";
           stmt.executeQuery(sql);
    }
    
    public static int getUserByName(String s) throws SQLException, ClassNotFoundException{
        Statement stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "SELECT TOP 1 * FROM User WHERE Name = \"" + s + "\";";
        ResultSet r = stmt.executeQuery(sql);
        int ret =  Integer.parseInt(r.getArray(0).toString());
        r.close();
        return ret;
    }

    public static ArrayList<String> getInfo() throws SQLException, ClassNotFoundException {
        Statement stmt = DBConnection.getCon().getConnection().createStatement();
        String sql = "SELECT * FROM User;";
        ResultSet r = stmt.executeQuery(sql);
        ArrayList<String> ret = new ArrayList<>();
        while(r.next()){
           
            String s = "id: "+ r.getInt(1)+" Nome: " + r.getString(2) + "Email: " + r.getString(3);
            ret.add(s);
        }
        r.close();
        return ret;
    }
}
