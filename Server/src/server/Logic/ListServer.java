/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Hugo
 */
public class ListServer{
    public static List<Server> listServer = new LinkedList<>();

    public void addServer(Server s){
        listServer.add(s);
        ordena();
    }
    
    public void removeServer(Server s){
        listServer.remove(s);
        ordena();
    }
    
    public static void ordena(){
        listServer.sort(new Comparator<Server>(){
           public int compare(Server a, Server b){
               return a.getNumberClientes() - b.getNumberClientes();
           } 
        });
    }
    
    public static String getServers(){
        String s = new String();
        s += "Existem : " + listServer.size() + "\n\n";
        for (Server server : listServer)
            s += "-> " + server.toString() + "\n";
        
        return s;
    }
    
}
