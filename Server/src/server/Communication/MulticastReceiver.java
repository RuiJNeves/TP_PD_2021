/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Communication;

import Features.Message;
import interfaces.ISendable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Logic.IServerSendable;
import server.Logic.ListServer;
import static server.Logic.ListServer.listServer;
import server.Logic.PingRequest;
import server.Logic.PingResponse;
import server.Logic.Server;

/**
 *
 * @author Hugo
 */
public class MulticastReceiver extends Thread{
    final static int PORT= 5432;
    public static final String List = "LIST";
    public static final int MAX = 1000;
    protected Server server;
    protected ISendable sendable;
    protected MulticastSocket s = null;
    protected boolean running;
    
    public MulticastReceiver(ISendable sendable, MulticastSocket s, Server server){
        this.sendable = sendable;
        this.s = s;
        this.server = server;
        running = false;
        
    }
    
    public void terminate(){
        running = false;
    }
    
    @Override
    public void run(){
        DatagramPacket pkt = null;
        ObjectInputStream in;
        ISendable sendable;
        Message msg;
        Object obj;
        ByteArrayOutputStream buff;
        ObjectOutputStream out;   
        if(s == null || !running){
            return;
        }
        try{
            
            while(running){ 
                
                
                pkt = new DatagramPacket(new byte[MAX], MAX);
                s.receive(pkt);
                
                //msg = new String(pkt.getData(), 0, pkt.getLength());
                try{
                    in = new ObjectInputStream(new ByteArrayInputStream(pkt.getData(), 0, pkt.getLength()));
                    obj = in.readObject();
                    
                    if(obj instanceof Message){
                        msg = (Message) obj;
                         
                    }else if(obj instanceof Server){
                        server = (Server)obj;
                        
                        if(!listServer.contains(server))
                            listServer.add(server);
                        
                    }else if(obj instanceof PingRequest){
                        
                        //thread que envia o ping tb apaga os "Mortos"
                        PingRequest request = (PingRequest) obj;
                        MulticastSender ms = MulticastSender(new PingResponse(myServer.getAddress()), null, s, InetAddress.getByName("230.30.30.30") );
                        ms.run();
                        
                    }else if(obj instanceof PingResponse){
                        
                        PingResponse response = (PingResponse) obj;
                        for(Server s : listServer){
                            if( s.getAddress()  == response.getAddress()){
                                s.stillAlive();
                            }
                        }
                        
                    }//ver restantes
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MulticastReceiver.class.getName()).log(Level.SEVERE, null, ex);
                }

                
                
            }
        } catch (IOException ex) {
            if(running)
            System.out.println(ex);
            
            if(!s.isClosed())
                s.close();
            Logger.getLogger(MulticastReceiver.class.getName()).log(Level.SEVERE, null, ex);

        }
    
    }
    
    
    
}
