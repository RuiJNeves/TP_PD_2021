package server.Communication;



import Helpers.ConnectionResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Logic.ListServer;
import server.Logic.Server;

/**
 *
 * @author Hugo
 */
public class UdpServer extends Thread{
    Server server;
    public static int MAX_SIZE = 1000;
    
    private static DatagramSocket socket = null;
    private static DatagramPacket packet;
    private static String receivedMsg;
    static int port ;
    private static  ByteArrayInputStream bin;
    private static  ObjectInputStream oin;
    private static  boolean running = true; //teste
    private static  ByteArrayOutputStream bout;
    private static  ObjectOutputStream oout;
    
    
    public UdpServer(Server s, int port) {
        server = s;
        this.port = port;
    }
        
    public void terminate(){
        running = false;
    }
    
     @Override
    public void run(){
        
        try{
            socket = new DatagramSocket(port);
            
            while(running){
                packet = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
                socket.receive(packet);
                
                bin = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
                oin = new ObjectInputStream(bin);
                
                //log = (Login)oin.readObject();
                receivedMsg = (String)oin.readObject();

                bout = new ByteArrayOutputStream();
                oout = new ObjectOutputStream(bout);
                
                if(receivedMsg.equals("connect")){
                    ListServer.ordena();
                    if(!ListServer.listServer.isEmpty() && (ListServer.listServer.get(0).getNumberClientes() * 100 / server.getNumberClientes())  < 50){
                        
                        HashMap map = new HashMap<InetAddress, Integer>();
                        for( Server s : ListServer.listServer){
                            map.put(s.getAddress(), s.getTcpPort());
                        }
                        
                        oout.writeObject(new ConnectionResponse(false, map, -1));
                        
                    }else{
                        oout.writeObject(new ConnectionResponse(true, null, server.getTcpPort()));
                    }
                    packet.setData(bout.toByteArray());
                    packet.setLength(bout.size());
                    socket.send(packet);
                }
            }
            
        }catch(NumberFormatException e){
            System.out.println("O porto de escuta deve ser um inteiro positivo.");
        } catch (SocketException ex) {
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
    }
     
}
