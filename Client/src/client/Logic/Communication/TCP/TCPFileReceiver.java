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
public class TCPFileReceiver implements Runnable{
    public static final int MAX_SIZE = 4000;
     
    File dir;
    Socket sckt;
    String fname;
    
    public TCPFileReceiver(Socket s, File f, String fileName){
        sckt = s;
        dir = f;
        fname = fileName;
    }

    @Override
    public void run() {
        
        FileOutputStream fout = null;
        PrintStream pout = null;
        InputStream in = null;
        String path = null;
        
        int nBytes;
        
        try{
            try{

                path = dir.getCanonicalPath()+File.separator+fname;
                fout = new FileOutputStream(path);
                
                System.out.println("Ficheiro " + path + " criado.");

            }catch(IOException e){

                if(path == null){
                    System.out.println("Ocorreu a excepcao {" + e +"} ao obter o caminho canonico para o ficheiro local!");   
                }else{
                    System.out.println("Ocorreu a excepcao {" + e +"} ao tentar criar o ficheiro " + path + "!");
                }

                return;
            }
            
            try{
                pout = new PrintStream(sckt.getOutputStream(), true);
                pout.println(fname);
                
                in = sckt.getInputStream();
                byte[] buffer = new byte[MAX_SIZE];
                while((nBytes = in.read(buffer)) > 0){
                    fout.write(buffer,0,nBytes);
                }
            }catch(Exception e){
                System.out.println("Erro - " + e.getMessage());
            }
        }catch(Exception e){
            System.out.println("Error - " + e.getMessage());
        }finally{
            try{
                if(sckt != null){
                    sckt.close();
                }
                if(fout != null){
                    fout.close();
                }
            }catch(Exception e){}
        }
        
    }
    
}
