/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.communication;
import Commons.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo
 */
public class MulticastSender extends Thread{
    DatagramPacket dgram;
    protected InetAddress group;
    protected ISendable sendable;
    protected MulticastSocket s = null;
    
    public MulticastSender(ISendable sendable, MulticastSocket s, InetAddress group ){
        this.s = s;
        this.sendable =sendable;
        this.group = group;
        
    }
    
    @Override
    public void run(){
        try {
            s.joinGroup(group);
        } catch (IOException ex) {
            Logger.getLogger(MulticastSender.class.getName()).log(Level.SEVERE, null, ex);
        }
            while(true){
                dgram = new DatagramPacket(sendable.getBytes(),sendable.length(),group,s.getPort());
            try {
                s.send(dgram);
            } catch (IOException ex) {
                Logger.getLogger(MulticastSender.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            
            
        }finally{
            
            if(t != null){
                t.terminate();
            }
            
            if(s != null){
                s.close();
            }
            
            //t.join(); //Para esperar que a thread termine caso esteja em modo daemon
            
        }
    }
}
