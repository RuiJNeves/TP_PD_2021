/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.communication;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
/**
 *
 * @author Hugo
 */
public class TcpFileReceiver implements Runnable{
    public static final int MAX_SIZE = 4000;
    public static final int TIMEOUT = 5; //segundos
    Socket socketToClient;
    File localDirectory;
    String fileName;
    
    public TcpFileReceiver(Socket s, File l, String f_name){
        socketToClient =s;
        localDirectory = l;
        fileName = f_name;
    }
    
    @Override
    public void run() {
        InputStream in;
        FileOutputStream out;
        String requestFileName, requestCanonicalFilePath = null;
        FileInputStream requestFileInputStream = null;
        
        byte []fileChunk = new byte[MAX_SIZE];
        int nbytes;
        try{
            socketToClient.setSoTimeout(TIMEOUT *1000);
            in = socketToClient.getInputStream();
                                                
            requestCanonicalFilePath = new File(localDirectory + File.separator + fileName).getCanonicalPath();
            
            if(!requestCanonicalFilePath.startsWith(localDirectory.getCanonicalPath()+File.separator)){
                System.out.println("Nao e' permitido aceder ao ficheiro " + requestCanonicalFilePath + "!");
                System.out.println("A directoria de base nao corresponde a " + localDirectory.getCanonicalPath()+"!");
                return;
            }
                        
            System.out.println("Ficheiro " + requestCanonicalFilePath + " aberto para leitura.");
            out = new FileOutputStream(requestCanonicalFilePath);

            while((nbytes = in.read(fileChunk)) >0){
                out.write(fileChunk,0,nbytes);
                System.out.println(fileChunk);
            }
            
            System.out.println("Transferencia concluida");
            
        }catch(SocketException e){
            System.out.println("Ocorreu uma excepcao ao nivel do socket TCP de ligacao ao cliente:\n\t"+e);
        }catch(FileNotFoundException e){
            System.out.println("Ocorreu a excepcao {" + e + "} ao tentar abrir o ficheiro " + requestCanonicalFilePath + "!");
        }catch(IOException e){
            System.out.println("Ocorreu uma excepcao de E/S durante o atendimento ao cliente actual: \n\t" + e);
        }finally{
            try{
                if(requestFileInputStream!=null){
                    requestFileInputStream.close();
                }
                socketToClient.close();
            }catch(IOException e){}
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
