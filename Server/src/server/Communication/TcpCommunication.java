/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo
 */
public class TcpCommunication  implements Runnable{
    public static final int MAX_SIZE = 256;
    int listeningPort;
    Socket socket = null;
    ServerSocket socketEscuta = null;
    BufferedReader bin;
    String receivedMsg;

    public TcpCommunication(int listeningPort, ServerSocket socketEscuta) {
        this.listeningPort = listeningPort;
        this.socketEscuta = socketEscuta;
    }

    @Override
    public void run() {
        
         while(true){
            
             try {
                 socket = socketEscuta.accept();
             } catch (IOException ex) {
                 Logger.getLogger(TcpCommunication.class.getName()).log(Level.SEVERE, null, ex);
             }
             
            try{
                bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream pout = new PrintStream(socket.getOutputStream(),true);
                receivedMsg = bin.readLine();

               
                //ler tipos de mensagens
                 

            }catch(IOException e){
                System.out.println("Problema " +e);
            }finally{
                 try {                    
                     socket.close();
                 } catch (IOException ex) {
                     Logger.getLogger(TcpCommunication.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
        }
        
        
    }

    
}
