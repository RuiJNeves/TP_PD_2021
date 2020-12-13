/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic;

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
        server_udp = new UdpServer(new Socket(new DatagramSocket(port)));
        MulticastSocket socket = new MulticastSocket(5432);
        socket.joinGroup(InetAddress.getByName("230.30.30.30"));
        m_receiver = new MulticastReceiver(socket, server, server.getAddress());
    }
    
    public void start(){
        server_udp.run();
        m_receiver.run();
    }
    
}
