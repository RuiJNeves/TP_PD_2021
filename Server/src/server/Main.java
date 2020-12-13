package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.*;
import server.Communication.TcpFileHandler;
import java.sql.*;
import server.Logic.Database.DBConnection;
import server.Logic.Server;


/**
 *
 * @author Hugo
 */
public class Main {
    
    public static final int MAX_SIZE = 1000;
    public Server server;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        /*String p = System.getProperty("user.dir");
        File savingDirectory = new File(p);

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            fname = in.readLine();
            TcpFileHandler fh = new TcpFileHandler(s);
            fh.setDirectoryToSend(savingDirectory);
            fh.setFileToSend(fname);
            System.out.println(fh.send());
            
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }*/
        
        Statement stmt = null;
        try{
         stmt = DBConnection.getCon().getConnection().createStatement();

            String sql = "Insert Into User(Name, Email)"
                    + "Values (\"Fabio\",\"asjd\");";

            stmt.executeQuery(sql);
        }catch(Exception ex){
            
        }
    }
}