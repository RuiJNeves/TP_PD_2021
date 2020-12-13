/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Communication;

import Features.Channel;
import Features.File;
import Features.Message;
import Helpers.ChannelEditor;
import Helpers.InfoRequest;
import Helpers.MessagesRequest;
import Helpers.StatsRequest;
import interfaces.IRequest;
import interfaces.ISendable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
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
    private boolean running;

    public TcpCommunication(int listeningPort, ServerSocket socketEscuta, TcpFileHandler file_handler, MulticastSocket s) {
        this.listeningPort = listeningPort;
        this.socketEscuta = socketEscuta;
        this.file_handler = file_handler;
        this.s = s;
    }

    @Override
    public void run() {
        ObjectOutputStream oout = null;
        ObjectInputStream oin = null;
        
        running = true;
        
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
                        System.out.println("");
                    else if(req instanceof StatsRequest)
                        System.out.println("");
                    else if(req instanceof MessagesRequest)
                        System.out.println("");
                }
               
                //ler tipos de mensagens
                 

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
}
