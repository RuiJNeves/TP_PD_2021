 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic.Communication.TCP;

import java.io.*;
import java.net.*;
import Features.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rui Neves
 */
public class TCPFileSender implements Runnable{
    
    final static int MAX_SIZE = 4000;
    final static int TIMEOUT = 5; //5ms
    
    Socket sckt;
    java.io.File fl;
    String fileName;
    
    public TCPFileSender(Socket s, java.io.File f, String fname){
        sckt = s;
        fl = f;
        fileName = fname;
    }

    @Override
    public void run() {
        BufferedReader in;
        OutputStream out;
        String canonicalPath = null;
        FileInputStream requestedStream = null;
        
        byte[] chunk = new byte[MAX_SIZE];
        int nBytes;
        
        try{
            sckt.setSoTimeout(TIMEOUT*1000);
            in = new BufferedReader(new InputStreamReader(sckt.getInputStream()));
            out = sckt.getOutputStream();
            
            canonicalPath = new java.io.File(fl + java.io.File.separator + fileName).getCanonicalPath();
            
            if(canonicalPath.startsWith(fl.getCanonicalPath()+ java.io.File.separator)){
                return;
            }
            ObjectOutputStream oout = new ObjectOutputStream(sckt.getOutputStream());
            oout.writeObject(new Features.File(fileName, canonicalPath));
            
            ObjectInputStream oin = new ObjectInputStream(sckt.getInputStream());
            boolean resp = (boolean)oin.readObject();

            if(resp){
                requestedStream = new FileInputStream(canonicalPath);
                while((nBytes = requestedStream.read(chunk)) > 0){
                    out.write(chunk);
                }
            }
         
        }catch(IOException | ClassNotFoundException e){
           
        }finally{
            try{
                if(requestedStream != null)
                    requestedStream.close();   
            }catch(IOException e){}
        }
    }
    
}
