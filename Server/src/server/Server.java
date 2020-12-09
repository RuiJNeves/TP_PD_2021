package server;

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
public class Server{
    int port;
    InetAddress addr;
    int nClientes;
    /**
     * @param args the command line arguments
     */
    public static void Server(int port, InetAddress addr){
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

    public int percentagem(int lotacao){
	return nClientes*100/lotacao;
    }
}