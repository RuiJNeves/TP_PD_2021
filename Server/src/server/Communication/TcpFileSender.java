package server.Communication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Hugo
 */
public class TcpFileSender implements Runnable{
    public static final int MAX_SIZE = 5000;
    public static final int TIMEOUT = 5; //segundos
        
    File localDirectory;
    Socket socket = null;
    String fileName = null;
    
    public TcpFileSender(Socket s, File f, String fName){
        localDirectory = f;
        socket = s;
        fileName = fName;
    }
    @Override
    public void run() {
        BufferedReader in;
        OutputStream out;
        String requestedCanonicalFilePath = null;
        FileInputStream requestedFileInputStream = null;
                
        byte[] fileChunk = new byte[MAX_SIZE];
        int nbytes;
        
        try{
            if(!localDirectory.exists()){
                System.out.println("ERRO a encontrar o ficheiro\n");
                return;
            }
            if(!localDirectory.isDirectory()){
                System.out.println("ERRO na diretoria");
                return;
            }
            
            if(!localDirectory.canRead()){
                System.out.println("ERRO permissao de leitura");
                return ;
            }
            
            socket.setSoTimeout(TIMEOUT * 1000);
            
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out= socket.getOutputStream();
                        
            requestedCanonicalFilePath = new File(localDirectory + File.separator + fileName).getCanonicalPath();
            
            if(!requestedCanonicalFilePath.startsWith(localDirectory.getCanonicalPath()+File.separator)){
                System.out.println("Nao e' permitido aceder ao ficheiro " + requestedCanonicalFilePath + "!");
                System.out.println("A directoria de base nao corresponde a " + localDirectory.getCanonicalPath()+"!");
                return;
            }
            
            requestedFileInputStream = new FileInputStream(requestedCanonicalFilePath);
            System.out.println("Ficheiro " + requestedCanonicalFilePath + " aberto para leitura.");

            while((nbytes = requestedFileInputStream.read(fileChunk))>0){
                out.write(fileChunk, 0, nbytes);
            }
            
            System.out.println("Transferencia concluida");
        } catch(SocketException e){
            System.out.println("Ocorreu uma excepcao ao nivel do socket TCP de ligacao ao cliente:\n\t"+e);
        }catch(FileNotFoundException e){
            System.out.println("Ocorreu a excepcao {" + e + "} ao tentar abrir o ficheiro " + requestedCanonicalFilePath + "!");
        }catch(IOException e){
            System.out.println("Ocorreu uma excepcao de E/S durante o atendimento ao cliente actual: \n\t" + e);
        }finally{
            try{
                if(requestedFileInputStream!=null){
                    requestedFileInputStream.close();
                }
                socket.close();
            }catch(IOException e){}
        }

    }
    
}
