/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic;

import Features.Message;
import client.Logic.Communication.TCP.TCPFileHandler;
import java.io.File;
import java.net.Socket;

/**
 *
 * @author ruizi
 */
public class Logic {
    
    private final String currentUser;
    private TCPFileHandler tcpFHan;
    
    public Logic(String user){
        currentUser = user;
        tcpFHan = new TCPFileHandler(new Socket());
    }
     
    public void sendMsg(String rcv,String msg){
        Message m = new Message(currentUser, rcv, msg);
        
    }

    public void sendFile(String file, String dir, String s) {

        tcpFHan.setFileToSend(file);
        File f = new File(dir);
        tcpFHan.setDirectoryToSend(f);
        tcpFHan.send();
    }
}
