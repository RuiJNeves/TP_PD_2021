/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic;

import java.net.InetAddress;
import server.Communication.CommsLogic;

/**
 *
 * @author Hugo
 */
public class Start {
    Server server;
    
    
    public Start( int port,  InetAddress addr){
        server = new Server(port, addr);
        CommsLogic c = new CommsLogic(server);
    }
    
    
}
