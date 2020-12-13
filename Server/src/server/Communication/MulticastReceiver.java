/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Communication;

import Features.Channel;
import Features.EnterChannel;
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
import server.Logic.Database.DataBaseFile;
import server.Logic.Database.DataBaseMessage;
import server.Logic.Database.DataBaseSentFileUser;
import server.Logic.Database.DataBaseUser;
import server.Logic.Database.DatabaseChanneUser;
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
    protected Server serve_r;
    protected ISendable sendable;
    protected MulticastSocket s = null;
    protected boolean running;
    protected InetAddress addr, bd;
    
    public MulticastReceiver(MulticastSocket s, Server server, InetAddress bd){
        this.s = s;
        this.serve_r = server;
        running = true;
        this.addr = server.getAddress();
        this.bd = bd;
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
                    
                    }else if(obj instanceof EnterChannel){
                        enter((EnterChannel)obj);
                        
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
                }

                
                
            }
        } catch (IOException ex) {
            if(running)
            System.out.println(ex);
            
            if(!s.isClosed())
                s.close();

        }finally{
            if(!s.isClosed())
                s.close();
        }
    
    }
    
    
    private boolean message(Message m){
        try {
            int rcv;
            int snd;
            DataBaseMessage.insert(m,bd);
            if(m.isToChannel())
                rcv = DatabaseChannel.getChannelByName(m.getUsernameRecive(),bd);
            else
                rcv = DataBaseUser.getUserByName(m.getUsernameRecive(),bd);
            snd = DataBaseUser.getUserByName(m.getUsernameSend(),bd);
            
            if(rcv != -1){
                DatabaseSentMessageUser.insert(m, snd, rcv,bd);


                MulticastSender ms = new MulticastSender(null, m, s );
                ms.run();
                return true;
            }
            return false;
        } catch (SQLException | ClassNotFoundException ex) {
            return false;
        }
    }
    
    private boolean file(File file) {
        if(file.isSending()){
            try{
                int rcv;
                int snd;
                DataBaseFile.insert(file,bd);
                snd = DataBaseUser.getUserByName(file.getSnd(),bd);
                if(file.isToChannel()){
                     rcv = DatabaseChannel.getChannelByName(file.getRcv(),bd);
                     DatabaseSentFileChannel.insert(snd, rcv, file,bd);
                }else{
                    rcv = DataBaseUser.getUserByName(file.getRcv(),bd);
                    DataBaseSentFileUser.insert(snd, rcv,file,bd);
                }

//                if(!file.isSending()){
//                    file_handler.setDirectoryToSend(new java.io.File(file.getDir()));
//                    file_handler.setFileToSend(file.getFile());
//                    file_handler.send();
//                }else{
//                    file_handler.dirToReceive(new java.io.File(file.getDir()));
//                    file_handler.receive();
//                }
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
            int idCreator = DataBaseUser.getUserByName(channelEditor.getCreator(),bd);
            
            if(channelEditor.getNome() == null && channelEditor.getCurrentPassword()== null){
                channel.setDescricao(channelEditor.getDescricao());
                channel.setNome(channelEditor.getNewNome());
                channel.setPassword(channelEditor.getPassword());
                channel.setIdCreator(idCreator);
                DatabaseChannel.insert(channel,bd);
                
            }else if(channelEditor.getNewNome() == null && channelEditor.getPassword() == null && channelEditor.getDescricao()==null){
                int idChannel = DatabaseChannel.getChannelByName(channelEditor.getNome(),bd);
                channel.setNome(channelEditor.getNome());
                channel.setPassword(channelEditor.getCurrentPassword());
                channel.setId(idChannel);
                DatabaseChannel.delete(channel,bd);
                
            }else{
                int idChannel = DatabaseChannel.getChannelByName(channelEditor.getNome(),bd);
                channel.setDescricao(channelEditor.getDescricao());
                channel.setNome(channelEditor.getNewNome());
                channel.setPassword(channelEditor.getPassword());
                channel.setId(idChannel);
                DatabaseChannel.update(channel,bd);
            }  
            
            MulticastSender ms = new MulticastSender(null, channelEditor, s );
            ms.run();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            return false;
        }
        
        
    }


    private boolean register(RegisterRequest r) {
        try {
            User user = new User(r.getNome(), r.getPass());
            user.setEmail(r.getEmail());
            
            DataBaseUser.insert(user,bd);

            return true;
        } catch (SQLException | ClassNotFoundException  ex) {     
            return false;
        }

    }
    
    private boolean enter(EnterChannel c) {
        try {
            int idC = DatabaseChannel.getChannelWithPass(c.getChannel(), c.getPass(), bd);
            int idU = DataBaseUser.getUserByName(c.getUser(), bd);
            Channel channel = new Channel();
            channel.setId(idC);
            
            User user = new User(c.getUser(), null);
            user.setId(idU);
            
            
            if(idC != -1){
                DatabaseChanneUser.insert(channel, user , bd);
            }else 
                return false;

            
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            return false;
        }

    }
}
