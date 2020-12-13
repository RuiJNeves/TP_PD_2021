/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic.Communication;

import Features.EnterChannel;
import Features.Login;
import Features.Message;
import Features.User;
import Helpers.ChannelEditor;
import Helpers.ConnectionResponse;
import Helpers.InfoRequest;
import Helpers.MessagesRequest;
import Helpers.RegisterRequest;
import Helpers.StatsRequest;
import client.Logic.Communication.TCP.TCPCommunicationClient;
import client.Logic.Communication.TCP.TCPFileHandler;
import client.Logic.Communication.TCP.TCPRequestSender;
import client.Logic.Communication.UDP.UDPCommunicationClient;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruizi
 */
public class ComsLogic {
    
    public final static int TIMEOUT = 10;
    
    private static Socket tcpSocket = null;
    private ObjectInputStream oin;
    private ObjectOutputStream oout;
    private TCPFileHandler tcpFHan;
    
    public ComsLogic(){
        
    }
    
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
        tcpFHan = new TCPFileHandler(tcpSocket);
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
        comClient.sendMsg(m);
    }

    public List<Message> sendMsgRequest(MessagesRequest req) {
        TCPRequestSender snd = new TCPRequestSender(tcpSocket, req);
        return snd.send();
    }
    
    public void sendFile(String file, File dir, String s, boolean toChannel, String r) {

        tcpFHan.setFileToSend(file);
        tcpFHan.setDirectoryToSend(dir);
        
        tcpFHan.send(new Features.File(file, dir.getName(), toChannel, s, r, true));
    }

    public void getFile(String file, File dir, String snd) {
        tcpFHan.setFileToRec(file);
        tcpFHan.setDirectoryToRec(dir);
        tcpFHan.receive();
    }
    
    public List<String> getStats(StatsRequest req){
        TCPRequestSender snd = new TCPRequestSender(tcpSocket, req);
        return snd.send();
    }
    
    public List<String> getInfo(InfoRequest req) {
        TCPRequestSender snd = new TCPRequestSender(tcpSocket, req);
        return snd.send();
    }
    
    public void sendChannel(ChannelEditor edt){
       TCPCommunicationClient snd = new TCPCommunicationClient(tcpSocket);
       snd.sendChannel(edt);
        
    }

    public boolean register(RegisterRequest reg) {
        boolean resp;
        try {
            oin = new ObjectInputStream(tcpSocket.getInputStream());
            oout = new ObjectOutputStream(tcpSocket.getOutputStream());
            
            oout.writeObject(reg);
            oout.flush();
            resp = (boolean)oin.readObject();
            return resp;
        } catch (IOException | ClassNotFoundException ex) {
            return false;
        }
    }

    public void enterChannel(EnterChannel ec) {
       TCPCommunicationClient comClient = new TCPCommunicationClient(tcpSocket);
       comClient.sendEnterChannel(ec);
    }

    public void finish() {
        try {
            tcpSocket.close();
        } catch (IOException ex) {
        }
    }
}
