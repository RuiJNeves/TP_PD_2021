/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.net.InetAddress;
import java.util.HashMap;

/**
 *
 * @author ruizi
 */
public class ConnectionResponse {
    
    private boolean resp;
    private HashMap<InetAddress, Integer> serverList;
    private int tcpPort;
    
    public ConnectionResponse(boolean r, HashMap m, int port){
        resp = r;
        serverList = m;
        tcpPort = port;
    }

    public boolean isResp() {
        return resp;
    }

    public void setResp(boolean resp) {
        this.resp = resp;
    }

    public int getPort() {
        return tcpPort;
    }

    public void setPort(int port) {
        tcpPort = port;
    }
    
    public HashMap<InetAddress, Integer> getServerList() {
        return serverList;
    }

    public void setServerList(HashMap<InetAddress, Integer> serverList) {
        this.serverList = serverList;
    }
    
    
}
