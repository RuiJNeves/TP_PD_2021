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
import Helpers.ChannelEditor;
import Helpers.RegisterRequest;
import interfaces.ISendable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Logic.Database.DataBaseFile;
import server.Logic.Database.DataBaseMessage;
import server.Logic.Database.DataBaseSentFileUser;
import server.Logic.Database.DataBaseUser;
import server.Logic.Database.DatabaseChannel;
import server.Logic.Database.DatabaseSentFileChannel;
import server.Logic.Database.DatabaseSentMessageUser;
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
    protected TcpFileHandler file_handler;
    protected Server serve_r;
    protected ISendable sendable;
    protected MulticastSocket s = null;
    protected boolean running;
    protected InetAddress addr;
    
    public MulticastReceiver(MulticastSocket s, Server server, InetAddress addr, TcpFileHandler file_handler){
        this.s = s;
        this.serve_r = server;
        running = true;
        this.addr = addr;
        this.file_handler = file_handler;
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
                        message((Message) obj);
                         
                    }else if(obj instanceof ChannelEditor){
                        channel((ChannelEditor) obj);
                    
                    }else if(obj instanceof File){
                        file((File) obj);
                        
                    }else if(obj instanceof RegisterRequest){
                        register((RegisterRequest) obj);
                    
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
    
    
    private boolean message(Message m){
        try {
            int rcv;
            int snd;
            DataBaseMessage.insert(m);
            if(m.isToChannel())
                rcv = DatabaseChannel.getChannelByName(m.getUsernameRecive());
            else
                rcv = DataBaseUser.getUserByName(m.getUsernameRecive());
            snd = DataBaseUser.getUserByName(m.getUsernameSend());
            DatabaseSentMessageUser.insert(m, snd, rcv);

            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            return false;
        }
    }
    
    private boolean file(File file) {
        if(file.isSending()){
            try{
                int rcv;
                int snd;
                DataBaseFile.insert(file);
                snd = DataBaseUser.getUserByName(file.getSnd());
                if(file.isToChannel()){
                     rcv = DatabaseChannel.getChannelByName(file.getRcv());
                     DatabaseSentFileChannel.insert(snd, rcv, file);
                }else{
                    rcv = DataBaseUser.getUserByName(file.getRcv());
                    DataBaseSentFileUser.insert(snd, rcv,file);
                }

                if(!file.isSending()){
                    file_handler.setDirectoryToSend(new java.io.File(file.getDir()));
                    file_handler.setFileToSend(file.getFile());
                    file_handler.send();
                }else{
                    file_handler.dirToReceive(new java.io.File(file.getDir()));
                    file_handler.receive();
                }
                return true;
            } catch (SQLException | ClassNotFoundException ex) {
                return false;
            }
            
        }else
            return true;
    }
    
    private boolean channel(ChannelEditor channelEditor) {
        try {
            Channel channel = new Channel();
            int idCreator = DataBaseUser.getUserByName(channelEditor.getCreator());
            
            if(channelEditor.getNome() == null && channelEditor.getCurrentPassword()== null){
                channel.setDescricao(channelEditor.getDescricao());
                channel.setNome(channelEditor.getNewNome());
                channel.setPassword(channelEditor.getPassword());
                channel.setIdCreator(idCreator);
                DatabaseChannel.insert(channel);
                
            }else if(channelEditor.getNewNome() == null && channelEditor.getPassword() == null && channelEditor.getDescricao()==null){
                int idChannel = DatabaseChannel.getChannelByName(channelEditor.getNome());
                channel.setNome(channelEditor.getNome());
                channel.setPassword(channelEditor.getCurrentPassword());
                channel.setId(idChannel);
                DatabaseChannel.delete(channel);
                
            }else{
                int idChannel = DatabaseChannel.getChannelByName(channelEditor.getNome());
                channel.setDescricao(channelEditor.getDescricao());
                channel.setNome(channelEditor.getNewNome());
                channel.setPassword(channelEditor.getPassword());
                channel.setId(idChannel);
                DatabaseChannel.update(channel);
            }  
            
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            return false;
        }
        
        
    }


    private boolean register(RegisterRequest r) {
        try {
            User user = new User(r.getNome(), r.getPass());
            user.setEmail(r.getEmail());
            
            DataBaseUser.insert(user);

            return true;
        } catch (SQLException | ClassNotFoundException  ex) {     
            return false;
        }

    }
}
