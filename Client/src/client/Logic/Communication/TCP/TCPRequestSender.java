/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic.Communication.TCP;

import Features.Message;
import Helpers.InfoRequest;
import Helpers.MessagesRequest;
import Helpers.StatsRequest;
import interfaces.IRequest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ruizi
 */
public class TCPRequestSender {
    private Socket s;
    private IRequest content;
    
    public TCPRequestSender(Socket s, IRequest content){
        this.s = s;
        this.content = content;
    }
    
    public List send() {
        ObjectOutputStream oout = null;
        ObjectInputStream oin = null;
        try {
            oin = new ObjectInputStream(s.getInputStream());
            oout = new ObjectOutputStream(s.getOutputStream());
            
            oout.writeObject(content);
            oout.flush();
            List lst = null;
            if(content instanceof MessagesRequest)
                lst = (ArrayList<Message>)oin.readObject();
            if(content instanceof StatsRequest || content instanceof InfoRequest)
                lst = (ArrayList<String>)oin.readObject();
            return lst;
            
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        } 
    }
}
