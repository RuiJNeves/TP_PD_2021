package server.Logic;

import java.net.InetAddress;
import server.Communication.CommsLogic;
import server.Logic.Server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hugo
 */
public class Logic {
    Server server;
    
    
    public Logic( int port,  InetAddress addr){
        server = new Server(port, addr);
        CommsLogic c = new CommsLogic(server);
    }
}
