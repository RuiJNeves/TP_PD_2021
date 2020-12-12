/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic.Communication.TCP;

import Features.Message;
import Helpers.ChannelEditor;
import interfaces.ISendable;
import java.net.Socket;

/**
 *
 * @author ruizi
 */
public class TCPCommunicationClient {
    
    private Socket s;
    private ISendable content;
    
    public TCPCommunicationClient(Socket s){
        this.s = s;
    }
    
    
    public void sendMsg(Message m){
        content = m;
        TCPSendableSender snd = new TCPSendableSender(s, m);
        Thread t = new Thread(snd);
        t.start();
        
    }
      
    public void sendChannel(ChannelEditor ce){
        content = ce;
        TCPSendableSender snd = new TCPSendableSender(s, ce);
        Thread t = new Thread(snd);
        t.start();
        
    }
}
