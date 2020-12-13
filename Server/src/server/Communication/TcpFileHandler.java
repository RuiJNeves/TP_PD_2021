/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Communication;

import java.io.File;
import java.net.Socket;

/**
 *
 * @author Hugo
 */
public class TcpFileHandler {
    String fileToSend;  
    Socket socket;
    File dirToSend, dirToReceive;
    
    boolean fileSendSet = false;
    boolean dirSendSet = false, dirReceiveSet = false;
    
    public TcpFileHandler(Socket socket){
        this.socket = socket;
    }
    
    public void setFileToSend(String f){
        fileToSend = f;
        fileSendSet = true;
    }
    
    public void setDirectoryToSend(File dir){
        dirToSend = dir;
        dirSendSet = true;
    }
    
    public void dirToReceive(File dir){
        dirToReceive = dir;
        dirReceiveSet = true;
    }
    
    public boolean send(){
        if(fileSendSet && dirSendSet){
            Thread tSend = new Thread(new TcpFileSender(socket, dirToSend, fileToSend));
            tSend.start();
            fileSendSet = dirSendSet= false;
            return true;
        }
        return false;
    }
    
    public boolean receive(){
        if(dirReceiveSet){
            Thread tReceive = new Thread(new TcpFileReceiver(socket, dirToReceive));
            tReceive.start();
            dirReceiveSet = false;
            return true;
        }
        return false;
    }
}
