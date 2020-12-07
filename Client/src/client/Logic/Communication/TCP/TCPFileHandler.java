/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic.Communication.TCP;

import java.io.*;
import java.net.*;

/**
 *
 * @author ruizi
 */
public class TCPFileHandler {
    
    String fileToSend, fileToReceive;
    Socket s;
    File dirToSend, dirToReceive;

    
    boolean fileSendSet, dirSendSet, fileRecSet, dirRecSet;
    
    public TCPFileHandler(Socket s){
        this.s = s;
        fileSendSet = false;
        dirSendSet = false;
        fileRecSet = false;
        dirRecSet = false;
    }
    
    public void setFileToSend(String f){
        fileToSend = f;
        fileSendSet = true;
    }
    
    public void setDirectoryToSend(File dir){
        dirToSend = dir;
        dirSendSet = true;
    }
    
    public void setFileToRec(String f){
        fileToReceive = f;
        fileRecSet = true;
    }
    
    public void setDirectoryToRec(File dir){
        dirToReceive = dir;
        dirRecSet = true;
    }
    
    public boolean send(){
        if(fileSendSet && dirSendSet){
            Thread tSend = new Thread(new TCPFileSender(s, dirToSend, fileToSend));
            tSend.start();
            fileSendSet = false;
            dirSendSet = false;
            return true;
        }
        return false;
    }
    
    
     public boolean receive(){
        if(fileRecSet && dirRecSet){
            Thread tRec = new Thread(new TCPFileReceiver(s, dirToReceive, fileToReceive));
            tRec.start();
            fileRecSet = false;
            dirRecSet = false;
            return true;
        }
        return false;
    }
    
    
}
