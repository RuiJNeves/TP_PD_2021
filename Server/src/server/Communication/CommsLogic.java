/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Communication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Logic.ListServer;
import server.Logic.Server;

/**
 *
 * @author Hugo
 */
public class CommsLogic {
    private int tcp, udp;
    Server server;
    UdpServer server_udp;
    InetAddress bd;
    MulticastReceiver m_receiver;
    TcpFileHandler file_handler;
    TcpCommunication tcp_comms;
    Socket  socket;
    //ServerSocket s_socket;

    public CommsLogic(int tcp, int udp, InetAddress bd) {
        this.bd = bd;
        this.tcp = tcp;
        this.udp = udp;
    }
    
    public void prepare(){
        MulticastSocket m_socket = null;
        try {
            m_socket = new MulticastSocket(5432);
            server = new Server(tcp, udp);
            server.setAddr(m_socket.getInetAddress());
            
            server_udp = new UdpServer(server, udp);
            
            
            m_socket.joinGroup(InetAddress.getByName("230.30.30.30"));
            if( m_socket == null)
                return;
        
            
            m_receiver = new MulticastReceiver(m_socket, server, bd);
            
            tcp_comms = new TcpCommunication(server.getTcpPort(), new ServerSocket(server.getTcpPort()),m_socket, bd);

        } catch (IOException ex) {
            
        }
    }
    
    public void start(){
        server_udp.setDaemon(true);
        server_udp.start();
        tcp_comms.setDaemon(true);
        tcp_comms.start();
        m_receiver.setDaemon(true);
        m_receiver.start();
    }
    
    public void finish(){
        try {
//            server_udp.terminate();
//            tcp_comms.terminate();
//            m_receiver.terminate();
            
            
            if(socket != null)
                socket.close();
            
        } catch (IOException ex) {
        }
    }
    
    public String getUsers(){
        return server.getClients();
    }
  
}
