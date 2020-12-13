/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Communication;

import Features.Channel;
import Features.File;
import Features.Login;
import Features.Message;
import Features.User;
import Helpers.ChannelEditor;
import Helpers.InfoRequest;
import Helpers.StatsRequest;
import Helpers.MessagesRequest;
import Helpers.RegisterRequest;
import interfaces.IRequest;
import interfaces.ISendable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Logic.Database.DataBaseFile;
import server.Logic.Database.DataBaseMessage;
import server.Logic.Database.DataBaseSentFileUser;
import server.Logic.Database.DataBaseSentMessageChannel;
import server.Logic.Database.DataBaseUser;
import server.Logic.Database.DatabaseChannel;
import server.Logic.Database.DatabaseSentFileChannel;
import server.Logic.Database.DatabaseSentMessageUser;

/**
 *
 * @author Hugo
 */
public class TcpCommunication  implements Runnable{
    public static final int MAX_SIZE = 256;
    private int listeningPort;
    private Socket socket = null;
    private MulticastSocket s;
    private ServerSocket socketEscuta = null;
    private Object receivedMsg;
    private TcpFileHandler file_handler;
    private ObjectOutputStream oout = null;
    private ObjectInputStream oin = null;
    private boolean running;

    public TcpCommunication(int listeningPort, ServerSocket socketEscuta, TcpFileHandler file_handler, MulticastSocket s) {
        this.listeningPort = listeningPort;
        this.socketEscuta = socketEscuta;
        this.file_handler = file_handler;
        this.s = s;
        running = true;
    }
    
    public void terminate(){
        running = false;
    }

    @Override
    public void run() {
        
     
         while(running){  
            try{
                socket = socketEscuta.accept();
                oin = new ObjectInputStream(socket.getInputStream());
                oout = new ObjectOutputStream(socket.getOutputStream());
              
                receivedMsg = oin.readObject();
                if(receivedMsg instanceof ISendable){
                    ISendable snd = (ISendable) receivedMsg;
                    if(snd instanceof Message)
                        message((Message)snd);
                    else if(snd instanceof File)
                       file((File)snd);
                    else if(snd instanceof ChannelEditor)
                        channel((ChannelEditor)snd);
                }else if(receivedMsg instanceof IRequest){
                    IRequest req = (IRequest) receivedMsg;
                    if(req instanceof InfoRequest)
                        info((InfoRequest) req);
                    else if(req instanceof StatsRequest)
                        stats((StatsRequest)req);
                    else if(req instanceof MessagesRequest)
                        messages((MessagesRequest)req);
                    else if(req instanceof Login)
                        login((Login) req);
                    else if(req instanceof RegisterRequest)
                        register((RegisterRequest)req);
                }
            }catch(IOException | ClassNotFoundException e){} 
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

            MulticastSender m_sender = new MulticastSender(null, m, s);
            m_sender.run();
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
    
    private boolean info(InfoRequest req){
        try{
           ArrayList<String> users = DataBaseUser.getInfo();
           ArrayList<String> channels = DatabaseChannel.getInfo();
           users.addAll(channels);
           
           oout.writeObject(users);
           oout.flush();
           //enviar os users como resposta
           return true;
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            return false;
        } 
    }

    private boolean stats(StatsRequest req) {
      try{
           int id = DatabaseChannel.getChannelByName(req.getChannelName());
           ArrayList<String> resp = DatabaseChannel.getStats(id);
           oout.writeObject(resp);
           oout.flush();
           
           return true;
        } catch (SQLException | ClassNotFoundException |IOException ex) {
            return false;
        }
    }

    private boolean messages(MessagesRequest req) {
       try{
           int id;
           ArrayList<String> resp;
           if(req.isFromChannel()){
               id = DatabaseChannel.getChannelByName(req.getOrg());
               resp = DataBaseSentMessageChannel.getMsgs(id,req.getNumMsgs());
           }else{
               id = DataBaseUser.getUserByName(req.getOrg());
               int me = DataBaseUser.getUserByName(req.getMe());
               resp = DatabaseSentMessageUser.getMsgs(id, req.getNumMsgs(), me);
           }
          
           oout.writeObject(resp);
           oout.flush();
           //enviar resp como resposta
           return true;
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            return false;
        } 
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
            
            
            
            MulticastSender m_sender = new MulticastSender(null, channelEditor , s);
            m_sender.run();
            
            
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            return false;
        }
        
        
    }

    private boolean login(Login login) {
        try {
            User user;
            user = DataBaseUser.loginUser(login.getNome(),login.getPass());

            if(user == null)
                user = new User(null,null);
            
            oout.writeObject(user);
            oout.flush();
            
            return true;
        } catch (SQLException | ClassNotFoundException |IOException ex) {
            return false;
        } 
    }

    private boolean register(RegisterRequest r) {
        try {
            User user = new User(r.getNome(), r.getPass());
            user.setEmail(r.getEmail());
            
            
            DataBaseUser.insert(user);
            
            oout.writeObject(true);
            oout.flush();
            
            MulticastSender m_sender = new MulticastSender(null, r, s);
            m_sender.run();
            return true;
        } catch (SQLException | ClassNotFoundException |IOException ex) {
            try {
                oout.writeBoolean(false);
                oout.flush();
            } catch (IOException ex1) {
            }
            
            return false;
        }
    }
}
