/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic;

import client.Logic.Communication.TCP.TCPFileHandler;
import Features.*;
import Helpers.*;
import client.Logic.Communication.*;
import java.io.File;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author ruizi
 */
public class Logic {
    
    private User currentUser;
    private TCPFileHandler tcpFHan;
    private ComsLogic cl;
    private boolean isLogged;
    
    public Logic(ComsLogic cl){
        tcpFHan = new TCPFileHandler(new Socket());
        this.cl = cl;
        isLogged = false;
    }
     
    public void sendMsg(String rcv,String msg){
        Message m;
        if(isLogged)
            m = new Message(currentUser.getNome(), rcv, msg);
        
    }

    public void sendFile(String file, String dir, String s) {

        tcpFHan.setFileToSend(file);
        File f = new File(dir);
        tcpFHan.setDirectoryToSend(f);
        tcpFHan.send();
    }

    public boolean login(Login log) {
        User r = cl.login(log);
        if(r != null)
            currentUser = r;
        return r != null;
    }
    
    public void sendMessage(String dest, String msg){
        Message m = new Message(currentUser.getNome(), dest, msg);
        cl.sendMsg(m);
        
    }

    public List<Message> getMsgs(String s, int n) {
        MessagesRequest req = new MessagesRequest(s, n);
        return cl.sendMsgRequest(req);
    }
}
