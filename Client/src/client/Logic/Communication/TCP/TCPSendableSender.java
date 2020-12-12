/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic.Communication.TCP;

import Helpers.ServerResponse;
import interfaces.ISendable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author ruizi
 */
public class TCPSendableSender implements Runnable{
    private Socket s;
    private ISendable content;
    
    public TCPSendableSender(Socket s, ISendable c){
        this.s = s;
        content = c;
    }

    @Override
    public void run() {
        ObjectOutputStream oout = null;
        ObjectInputStream oin = null;
        try {
         
            
            oin = new ObjectInputStream(s.getInputStream());
            oout = new ObjectOutputStream(s.getOutputStream());
            
            oout.writeObject(content);
            oout.flush();
           
            
        } catch (IOException ex) {}     
    }
    
}
