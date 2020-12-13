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
    Server server;
    UdpServer server_udp;
    MulticastReceiver m_receiver;
    TcpFileHandler file_handler;
    TcpCommunication tcp_comms;
    Socket  socket;

    public CommsLogic(Server server) {
        this.server = server;
        
        try {
            socket = new Socket(server.getAddress(), server.getPort());
        } catch (IOException ex) {
            Logger.getLogger(CommsLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        file_handler = new TcpFileHandler(socket);
    }
    
    public void start(){
        try {
            MulticastSocket m_socket = null;
            try {
                server_udp = new UdpServer(server);
                m_socket = new MulticastSocket(5432);
                
                m_socket.joinGroup(InetAddress.getByName("230.30.30.30"));
                
            } catch (IOException ex) {
                
            }
            
            if( m_socket == null)
                return;
            
            m_receiver = new MulticastReceiver(m_socket, server, server.getAddress());
            
            try {
                tcp_comms = new TcpCommunication(server.getPort(), new ServerSocket(server.getPort()));
            } catch (IOException ex) {
                Logger.getLogger(CommsLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Scanner scanner = new Scanner(System.in);
            String s;
            while((s = scanner.nextLine()).equalsIgnoreCase("shutdown")){
                if(s.equalsIgnoreCase("Clients")){
                    System.out.println(server.getClients());
                }
                
                if(s.equalsIgnoreCase("Servers")){
                    System.out.println(ListServer.getServers());
                }
            }
            
            server_udp.terminate();
            m_receiver.terminate();
            
            socket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(CommsLogic.class.getName()).log(Level.SEVERE, null, ex);       
        }
        
    }
    
    
    
    
    
    
}
