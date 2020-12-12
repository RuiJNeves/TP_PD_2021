/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic.Communication;

import Features.Login;
import Features.Message;
import Features.User;
import Helpers.ConnectionResponse;
import Helpers.MessagesRequest;
import client.Logic.Communication.TCP.TCPCommunicationClient;
import client.Logic.Communication.UDP.UDPCommunicationClient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 *
 * @author ruizi
 */
public class ComsLogic {
    
    public final static int TIMEOUT = 10;
    
    private static Socket tcpSocket = null;
    private ObjectInputStream oin;
    private ObjectOutputStream oout;
    
    public ComsLogic(){}
    
    /**
     * 
     * @param address - string to convert to inet address
     * @param port - string to convert to int for the port
     * @return -1 error; 0 did not connect; 1 connected
     */
    public int connect(String address, String port){
        UDPCommunicationClient com = null;
        
        try{
            InetAddress addr = InetAddress.getByName(address);
            int p = Integer.parseInt(port);
            ConnectionResponse r = con(addr, p, com);
            if(r.isResp()){
                tcpConnect(addr, r.getPort());
                return 1;
            }else
                return retry(r);
        }catch(IOException | NumberFormatException e){
            return -1;
        }finally{
            if(com != null)
                com.close();
        }
    }
    
    
    private int retry(ConnectionResponse r){
        List<InetAddress> l = (List<InetAddress>) r.getServerList().keySet();
        UDPCommunicationClient com = null;
        for (InetAddress i : l) {
            try{
                int p = r.getServerList().get(i);
                com = new UDPCommunicationClient(i, p);
                ConnectionResponse rsp = con(i, p,com);
                if(rsp.isResp()){
                    tcpConnect(i, rsp.getPort());
                    return 1;
                }
                
            }catch(Exception e){
                return -1;
            }
            finally{
                if(com != null)
                    com.close();
            }   
        }
        return 0;
    }
    
    private ConnectionResponse con(InetAddress addr, int p, UDPCommunicationClient com) throws UnknownHostException{
        
        com = new UDPCommunicationClient(addr, p);
        return com.sendConnection();
    }
    
    private void tcpConnect(InetAddress server, int port) throws IOException {
        tcpSocket = new Socket(server, port);
        tcpSocket.setSoTimeout(TIMEOUT*1000);
    }
    
    public User login(Login l){
        User resp;
        try {
            oin = new ObjectInputStream(tcpSocket.getInputStream());
            oout = new ObjectOutputStream(tcpSocket.getOutputStream());
            
            oout.writeObject(l);
            oout.flush();
            resp = (User)oin.readObject();
            return resp;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public void sendMsg(Message m) {
        TCPCommunicationClient comClient = new TCPCommunicationClient(tcpSocket);
        comClient.send(m);
    }

    public List<Message> sendMsgRequest(MessagesRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
