package server.communication;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Hugo
 */
public class UdpServer {
    
    public static int MAX_SIZE = 1000;
    
    private static  int listeningPort;
    private static DatagramSocket socket = null;
    private static DatagramPacket packet;
    private static String receivedMsg;
    //Login log = null;
    private static  ByteArrayInputStream bin;
    private static  ObjectInputStream oin;
    private static  boolean t = true; //teste
    private static  ByteArrayOutputStream bout;
    private static  ObjectOutputStream oout;
    
    
    /*Recebe o porto*/
    public static void main(String[] args) throws IOException {
               
        if(args.length!=1){ 
            System.out.println("Sintaxe: java UdpTimeServer_v2 listeningPort");
            return;
        }
        
        try{
            listeningPort = Integer.parseInt(args[0]);
            socket = new DatagramSocket(listeningPort);
            System.out.println("UDP Time Server Started...");
            
            while(true){
                packet = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
                socket.receive(packet);
                
                bin = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
                oin = new ObjectInputStream(bin);
                
                //log = (Login)oin.readObject();
                
                System.out.println("Recebido \"" /*+ log.toString()*/ + "\" de " + 
                    packet.getAddress().getHostAddress() + ":" + packet.getPort());
                
                bout = new ByteArrayOutputStream();
                oout = new ObjectOutputStream(bout);
                oout.writeObject(t);
                
                packet.setData(bout.toByteArray());
                packet.setLength(bout.size());
                socket.send(packet);
            }
            
        }catch(NumberFormatException e){
            System.out.println("O porto de escuta deve ser um inteiro positivo.");
        }
    }
    
}
