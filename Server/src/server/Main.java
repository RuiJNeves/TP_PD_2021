package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Logic.Logic;


/**
 *
 * @author Hugo
 */
public class Main {
    
    public static final int MAX_SIZE = 1000;
    static Logic logic;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        InetAddress db;
        try {
            int udp_port = Integer.parseInt(args[1]);
            db = InetAddress.getByName(args[2]);
            int tcp_port = Integer.parseInt(args[0]);
            logic = new Logic(tcp_port, udp_port, db);
            logic.work();
        } catch (UnknownHostException ex) {
        }
        
    }
}