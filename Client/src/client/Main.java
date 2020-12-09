package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import client.Logic.Communication.ComsLogic;
import client.UI.Text.TextInterface;

/**
 *
 * @author Rui Neves
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         ComsLogic l;
        if(args.length < 2){
            System.out.println("Sintaxe inválida! Indique IP e PORTO do servidor");
        }else{
            System.out.println("A conectar...");
           
            l = new ComsLogic();
            
            switch(l.connect(args[0], args[1])){
                case 0 -> System.out.println("Impossivel connectar! Lamentamos... Tente mais tarde");
                case 1 -> {
                    TextInterface i = new TextInterface(l);
                    i.run();
                    System.out.println("Connectado");
                 }
                default -> System.out.println("Ocorreu um erro inesperado! Tente outra vez!");
            } 
        }   
    }
}
