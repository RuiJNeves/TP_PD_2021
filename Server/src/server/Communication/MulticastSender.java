/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Communication;

import interfaces.ISendable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import server.Logic.IServerSendable;





/**
 *
 * @author Hugo
 */
public class MulticastSender extends Thread{
    public static final int MAX = 1000;
    private static final String PING = "PING";
    
    final static int PORT= 5432;
    DatagramPacket dgram;
    protected ISendable user_sendable;
    protected IServerSendable sendable;
    protected MulticastSocket m_socket = null;
    
    public MulticastSender(IServerSendable sendable, ISendable user_sendable, MulticastSocket s){
        if(s == null) return;
        this.m_socket = s;
        this.sendable =sendable;
        this.user_sendable = user_sendable;
        
    }
    
    
    @Override
    public void run(){
        DatagramPacket pk;
        ByteArrayOutputStream buffer;
        ObjectOutputStream oout;   
        
        try {
            
            pk = new DatagramPacket(new byte[MAX], MAX);
            try {
                buffer = new ByteArrayOutputStream();
                oout = new ObjectOutputStream(buffer);

                if(user_sendable != null){           
                    oout.writeObject(user_sendable);

                }else if(sendable != null){               
                    oout.writeObject(sendable);
                }else
                    oout.writeObject(PING);

                oout.flush(); 
                oout.close();

                pk.setData(buffer.toByteArray());
                pk.setLength(buffer.size());
                m_socket.send(pk); 
            } catch (IOException ex) {
            }
                       
        }catch (Exception ex) {
        }
    }
}
