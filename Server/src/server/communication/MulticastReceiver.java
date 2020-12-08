/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo
 */
public class MulticastReceiver extends Thread{
    public static final String List = "LIST";
    public static final int MAX = 1000;
    
    protected ISendable sendable;
    protected MulticastSocket s = null;
    protected boolean running = false;
    
    public MulticastReceiver(ISendable sendable, MulticastSocket s){
        this.sendable = sendable;
        this.s = s;
        running = true;
        
    }
    
    public void terminate(){
        running = false;
    }
    
    @Override
    public void run(){
        DatagramPacket pkt = null;
        String msg;
        if(s == null || !running){
            return;
        }
        try{
            
            while(running){
                
                
                pkt = new DatagramPacket(new byte[MAX], MAX);
                s.receive(pkt);
                
                msg = new String(pkt.getData(), 0, pkt.getLength());
                
                if(msg.toUpperCase().contains(List.toUpperCase())){
                    pkt.setData(sendable.getBytes());
                    pkt.setLength(sendable.length());               
                    s.send(pkt);
                }
                System.out.println();
                System.out.println("(" + pkt.getAddress().getHostAddress() + ":" + 
                        pkt.getPort() + ") " + msg);
                System.out.println(); System.out.print("> ");
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
