

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import server.Communication.TcpFileHandler;

/**
 *
 * @author Hugo
 */
public class Main {
    
    public static final int MAX_SIZE = 1000;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        String p = System.getProperty("user.dir");
        File savingDirectory = new File(p);
        
        BufferedReader in = null;

        String fname;
        ServerSocket socket = null;
        Socket s;

        try{
            socket = new ServerSocket(6001);
            s = socket.accept();
            
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            fname = in.readLine();
            TcpFileHandler fh = new TcpFileHandler(s);
            fh.setDirectoryToSend(savingDirectory);
            fh.setFileToSend(fname);
            System.out.println(fh.send());
            
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }
}