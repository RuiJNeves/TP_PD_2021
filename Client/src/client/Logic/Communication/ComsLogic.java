/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic.Communication;

import Helpers.ConnectionResponse;
import client.Logic.Communication.UDP.UDPCommunicationClient;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 *
 * @author ruizi
 */
public class ComsLogic {
    

    
    public ComsLogic(){}
    
    /**
     * 
     * @param address - string to convert to inet address
     * @param port - string to convert to int for the port
     * @return -1 error; 0 did not connect; 1 connected
     */
    public int connect(String address, String port){
        UDPCommunicationClient com = null;
        
        try{
            InetAddress addr = InetAddress.getByName(address);
            int p = Integer.parseInt(port);
            ConnectionResponse r = con(addr, p, com);
            if(r.isResp())
                return 1;
            else
                return retry(r);
        }catch(NumberFormatException | UnknownHostException e){
            return -1;
        }finally{
            if(com != null)
                com.close();
        }
    }
    
    
    private int retry(ConnectionResponse r){
        List<InetAddress> l = (List<InetAddress>) r.getServerList().keySet();
        UDPCommunicationClient com = null;
        for (InetAddress i : l) {
            try{
                int p = r.getServerList().get(i);
                com = new UDPCommunicationClient(i, p);
                if(con(i, p,com).isResp())
                    return 1;
                
            }catch(Exception e){
                return -1;
            }
            finally{
                if(com != null)
                    com.close();
            }   
        }
        return 0;
    }
    
    
    private ConnectionResponse con(InetAddress addr, int p, UDPCommunicationClient com) throws UnknownHostException{
        
        com = new UDPCommunicationClient(addr, p);
        return com.sendConnection();
    }
}
