/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Communication;

import Features.Channel;
import Features.File;
import Features.Message;
import Features.User;
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
    protected Server serve_r;
    protected ISendable sendable;
    protected MulticastSocket s = null;
    protected boolean running;
    protected InetAddress addr;
    
    public MulticastReceiver(MulticastSocket s, Server server, InetAddress addr){
        this.s = s;
        this.serve_r = server;
        running = true;
        this.addr = addr;
    }
    
    public void terminate(){
        running = false;
    }
    
    @Override
    public void run(){
        DatagramPacket pkt = null;
        ObjectInputStream in;
        Message msg;
        User usr;
        Channel channel;
        File file;
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
                
                try{
                    in = new ObjectInputStream(new ByteArrayInputStream(pkt.getData(), 0, pkt.getLength()));
                    obj = in.readObject();
                    
                    if(obj instanceof Message){
                        msg = (Message) obj;
                        //guardar a mensagem na base de dados
                         
                    }else if(obj instanceof User){
                        usr = (User) obj;
                        //guardar na base de dados
                    
                    }else if(obj instanceof Channel){
                        channel = (Channel) obj;
                        //guardar na base de dados
                    
                    }else if(obj instanceof File){
                        file = (File) obj;
                        //guardar na base de dados
                    
                    }else if(obj instanceof Server){
                        serve_r = (Server)obj;
                        
                        if(!listServer.contains(serve_r))
                            listServer.add(serve_r);
                        else
                            listServer.get(listServer.indexOf(serve_r)).setClient(serve_r.getNumberClientes());
                        
                    }else if(obj instanceof PingRequest){
                        
                        PingRequest request = (PingRequest) obj;
                        MulticastSender ms = new MulticastSender(new PingResponse(serve_r), null, s );
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
