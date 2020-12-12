

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.*;
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
    public void main(String[] args) throws UnknownHostException{
        int port = Integer.parseInt(args[1]);
        InetAddress addr = InetAddress.getByName(args[2]);
        server = new Server(port, addr);
        
            

    }
}