/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic;

import java.net.InetAddress;

/**
 *
 * @author Hugo
 */
public class PingResponse implements IServerSendable {
    InetAddress inet;
    
    public PingResponse(InetAddress addr){
        inet = addr;
    }
    
    public InetAddress getAddress(){
        return inet;
    }
    
}
