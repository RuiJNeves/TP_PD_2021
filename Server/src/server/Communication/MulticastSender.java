/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Communication;


import interfaces.ISendable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Hugo
 */
public class MulticastSender extends Thread{
    final static int PORT= 5432;
    boolean running = true;
    DatagramPacket dgram;
    protected InetAddress group;
    protected ISendable sendable;
    protected MulticastSocket s = null;
    
    public MulticastSender(ISendable sendable, MulticastSocket s, InetAddress group ){
        this.s = s;
        this.sendable =sendable;
        this.group = group;
        
    }
    
    public void terminate()
    {                
        running = false;
    }
    
    @Override
    public void run(){
        try {
            s.joinGroup(group);
            while(running){
                dgram = new DatagramPacket(sendable.getContent().getBytes(),sendable.getContent().length(),group,s.getPort());
                try {
                    s.send(dgram);
                } catch (IOException ex) {
                    Logger.getLogger(MulticastSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(MulticastSender.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
            
            if(s != null){
                s.close();
            }
            
        
        
                
            
            
        
            //t.join(); //Para esperar que a thread termine caso esteja em modo daemon
            
        }
    }
}
