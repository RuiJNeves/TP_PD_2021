/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Communication.MulticastReceiver;
import server.Communication.UdpServer;

/**
 *
 * @author Hugo
 */
public class Start {
    Server server;
    UdpServer server_udp;
    MulticastReceiver m_receiver;
    
    public Start( int port,  InetAddress addr){
        server = new Server(port, addr);
        MulticastSocket socket = null;
        try {
            server_udp = new UdpServer(server);
            socket = new MulticastSocket(5432);
            socket.joinGroup(InetAddress.getByName("230.30.30.30"));
        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( socket == null)
            return;
        
        m_receiver = new MulticastReceiver(socket, server, server.getAddress());
    }
    
    public void start(){
        server_udp.run();
        m_receiver.run();
    }
    
}
