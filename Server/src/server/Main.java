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
        InetAddress addr;
        try {
            addr = InetAddress.getByName(args[2]);
            int i = Integer.parseInt(args[1]);
            logic = new Logic(i, addr);
            //start.work();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}