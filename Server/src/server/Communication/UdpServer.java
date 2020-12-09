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
import java.util.HashMap;
import server.Logic.ListServer;
import server.Logic.Server;

/**
 *
 * @author Hugo
 */
public class UdpServer {
    private static int tcpPort;
    public static int MAX_SIZE = 1000;
    
    private static DatagramSocket socket = null;
    private static DatagramPacket packet;
    private static String receivedMsg;

    private static  ByteArrayInputStream bin;
    private static  ObjectInputStream oin;
    private static  boolean t = true; //teste
    private static  ByteArrayOutputStream bout;
    private static  ObjectOutputStream oout;
    
    
    public UdpServer(Server s) {
        this.tcpPort = port;
        
    }
        
    /*Recebe o porto*/
    public void ListentUDP(int lp) throws IOException, ClassNotFoundException {
        
        try{
            socket = new DatagramSocket(lp);
            
            while(true){
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
                    if(ListServer.listServer.get(0).getClientes() * 100 /  < 50){
                        
                        HashMap map = new HashMap<InetAddress, Integer>();
                        for( Server s : ListServer.listServer){
                            map.put(s.getAddress(), s.getPort());
                        }
                        
                        oout.writeObject(new ConnectionResponse(false, map, -1));
                        
                    }else{
                        oout.writeObject(new ConnectionResponse(true, null, tcpPort));
                    }
                    packet.setData(bout.toByteArray());
                    packet.setLength(bout.size());
                    socket.send(packet);
                }
            }
            
        }catch(NumberFormatException e){
            System.out.println("O porto de escuta deve ser um inteiro positivo.");
        }
    }
    


    
}
