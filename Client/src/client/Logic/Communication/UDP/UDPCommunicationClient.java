/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic.Communication.UDP;

import Helpers.ConnectionResponse;
import java.io.*;
import java.net.*;

/**
 *
 * @author ruizi
 */
public class UDPCommunicationClient {
    private final static int TIMEOUT = 10;
    private final static int SIZE = 500;
    private final static String CON = "connect";
    
    private DatagramSocket ds;
    private DatagramPacket dpIn, dpOut;
    
    private InetAddress serverAddress;
    private int serverPort;
    
    private ByteArrayInputStream bin;
    private ByteArrayOutputStream bout;
    
    private ObjectInputStream oin;
    private ObjectOutputStream oout;
    
    private boolean sendable;
    
    public UDPCommunicationClient(InetAddress addr, int port){
        try{
            bout = new ByteArrayOutputStream();
            oout = new ObjectOutputStream(bout);
            
            serverAddress = addr;
            serverPort = port;
            
            ds = new DatagramSocket();
            ds.setSoTimeout(TIMEOUT*1000);
            
            sendable = false;
            
        }catch(Exception e){
            System.out.println("Erro");
        }
    }
    
    public boolean setContent(Object o){
        try{
            oout.writeObject(o);
            dpOut = new DatagramPacket(bout.toByteArray(), bout.size(), serverAddress, serverPort);
            sendable = true;
            return true;
        }catch(Exception e){
            System.out.println("Erro");
            sendable  = false;
            return false;
        }
    }
    
     public boolean sendContent(){
        if(sendable){
            try{
                ds.send(dpOut);
                dpIn = new DatagramPacket(new byte[SIZE], SIZE);

                ds.receive(dpIn);
                bin = new ByteArrayInputStream(dpIn.getData(), 0, dpIn.getLength());
                oin = new ObjectInputStream(bin);

                boolean response = (boolean)oin.readObject();
                sendable = false;
                return response;
            }catch(Exception e){
                System.out.println("Erro");
                sendable = false;
                return false;
            }
        }else
            return false;
    }
    
    public ConnectionResponse sendConnection(){
        if(!sendable){
            setContent(CON);
            try{
                ds.send(dpOut);
                dpIn = new DatagramPacket(new byte[SIZE], SIZE);

                ds.receive(dpIn);
                bin = new ByteArrayInputStream(dpIn.getData(), 0, dpIn.getLength());
                oin = new ObjectInputStream(bin);

                ConnectionResponse response = (ConnectionResponse)oin.readObject();
                sendable = false;
                return response;
            }catch(Exception e){
                System.out.println("Erro");
                sendable = false;
                return null;
            }
        }else
            return null;
    }
         
    
    public void close(){
        ds.close();
    }
    
}
