package server.Logic;

import java.net.InetAddress;

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
    boolean check = false;
    
    
    public Server(int port, InetAddress addr){
        this.port = port;
	this.addr = addr;
	nClientes = 0;
    }

    public InetAddress getAddress(){
	return addr;
    }

    public int getPort(){
	return port;
    }

    public void incrementa(){
	nClientes++;
    }

    public int getClientes(){
	return nClientes;
    }

    public void stillAlive() {
        check = true;
    }
    
    public void prepareCheck(){
        check = false;
    }
}