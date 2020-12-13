package server.Logic;

import Features.User;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Hugo
 */
public class Server implements IServerSendable{
    int port;
    InetAddress addr;
    int nClientes;
    List<String> clientList;  
    boolean check = false;
    
    
    public Server(int port, InetAddress addr){
        this.port = port;
	this.addr = addr;
	nClientes = 0;
        clientList = new LinkedList<>();
    }

    public InetAddress getAddress(){
	return addr;
    }

    public int getPort(){
	return port;
    }

    public boolean addUser(String ip){
        if(clientList.contains(ip) || ip == null)
            return false;
        
        clientList.add(ip);
	nClientes++;
        return true;
    }

    public int getNumberClientes(){
	return nClientes;
    }

    public void stillAlive() {
        check = true;
    }
    
    public void prepareCheck(){
        check = false;
    }
    
    public void setClient(int clients){
        nClientes = clients;
    }

    public String getClients() {
        String s = new String();
        s += "Ligados : " + getNumberClientes() + "\n\n";
        for (String l : clientList)
            s += "-> " + l + "\n";
        
        return s;
    }
    
    @Override
    public String toString(){
        return "Server - " + addr.toString() +" - with - " + getNumberClientes();
    }
    
    
}