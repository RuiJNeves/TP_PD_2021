package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.*;
import server.Communication.TcpFileHandler;
import java.sql.*;
import server.Logic.Database.DBConnection;
import server.Logic.Server;


/**
 *
 * @author Hugo
 */
public class Main {
    
    public static final int MAX_SIZE = 1000;
    public Start start;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        start = new Start();
    }
}