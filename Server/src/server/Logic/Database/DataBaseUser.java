package server.Logic.Database;

import Features.User;
import java.sql.*;

/**
 *
 * @author Fabio
 */
public class DataBaseUser {
    
    public void create(User user) throws SQLException, ClassNotFoundException{
        Statement stmt = null;

        stmt = (Statement) DBConnection.getCon().getConnection().createStatement();

        String sql = "Insert Into User(Name, Email, Password) "
                    + "Values (\""+user.getNome()+"\", \""+user.getEmail() +"\", " + user.getPassword() + ");" ;

        stmt.executeQuery(sql);

    }
    
    public void delete(int id) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        try{
         stmt = (Statement) DBConnection.getCon().getConnection().createStatement();

            String sql = "Delete from User "
                    + "Where idUser" + id + ";";

            stmt.executeQuery(sql);
        }catch(Exception ex){

        }
    }
    
    public void update(int id, User user) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        try{
         stmt = (Statement) DBConnection.getCon().getConnection().createStatement();

            String sql = "Update User "
                    + "Set Name="+ user.getNome() + ", Email="+ user.getEmail()+ ", Password=" + user.getPassword()+ " "
                    + "Where idUser="+ id + ";";
            stmt.executeQuery(sql);
        }catch(Exception ex){

        }
    }
}
