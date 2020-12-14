package server.Logic;

import java.net.InetAddress;
import java.util.Scanner;
import server.Communication.CommsLogic;
import server.Logic.Server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hugo
 */
public class Logic {
    InetAddress bd;
    CommsLogic c;
    
    public Logic( int port,  int udp_port, InetAddress bd){
        this.bd = bd;
        c = new CommsLogic(port,udp_port , bd);
    }
    
    public void work(){
        Scanner scanner = new Scanner(System.in);
        String s;
        c.prepare();
        c.start();
        System.out.println("Running");
        while(!((s = scanner.nextLine()).equalsIgnoreCase("shutdown"))){
          
            if(s.equalsIgnoreCase("Clients")){
                System.out.println(c.getUsers());
            }

            if(s.equalsIgnoreCase("Servers")){
                System.out.println(ListServer.getServers());
            }
        }

        c.finish();
    }
}
