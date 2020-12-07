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
 * @author Rui Neves
 */
public class TCPFileSender implements Runnable{
    
    final static int MAX_SIZE = 4000;
    final static int TIMEOUT = 5; //5ms
    
    Socket sckt;
    File fl;
    String fileName;
    
    public TCPFileSender(Socket s, File f, String fname){
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
            
            canonicalPath = new File(fl + File.separator + fileName).getCanonicalPath();
            
            if(canonicalPath.startsWith(fl.getCanonicalPath()+File.separator)){
                System.out.println("Error");
                return;
            }
            
            requestedStream = new FileInputStream(canonicalPath);
            while((nBytes = requestedStream.read(chunk)) > 0){
                out.write(chunk);
            }
            System.out.println("File transfered");
        }catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }finally{
            try{
                if(requestedStream != null)
                    requestedStream.close();
                
                sckt.close(); //temos de pensar isto
            }catch(IOException e){}
        }
    }
    
}
