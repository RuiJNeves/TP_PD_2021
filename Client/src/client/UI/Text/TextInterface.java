/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.UI.Text;
import client.Logic.Logic;
import java.util.Scanner;

/**
 *
 * @author ruizi
 */
public class TextInterface {
    
    private final String[] MENU = {"Enviar Mensagem", "Enviar Ficheiro", "Receber Mensagen", "Download Ficheiro", "Criar Canal", 
                                    "Editar Canal", "Eliminar Canal", "Listar Canais/Utilizadores","Estatisticas"};
    private final Scanner s;
    private Logic l;
    public TextInterface(){
        s = new Scanner(System.in);
        l = new Logic("Rui");
    }
    
    public void run(){
        int opt;
        do{
            opt = writeMenu(MENU);
            computeOption(opt);
            
        }while(opt != 0);
    }
    
    private int writeMenu(String[] opts){
        for(int i = 0; i < opts.length; i++){
            System.out.println(i+1 + ". " + opts[i]);
        }
        System.out.println("0. Sair");
        return readInt();
    }
    
    private int readInt(){
        return s.nextInt();
    }
    
    private void computeOption(int o){
        switch(o){
            case 1 -> sendMsg();
            case 2 -> sendFile();
            case 3 -> rcvMsg();
            case 4 -> download();
            case 5 -> createChannel();
            case 6 -> editChannel();
            case 7 -> deleteChannel();
            case 8 -> listInfo();
            case 9 -> stats();
            default -> {return;}
        }
    }

    private void sendMsg() {
        System.out.println("Indique o destinarior:");
        String rcv = s.nextLine();
        System.out.println("Indique o conteúdo:");
        String msg = s.nextLine();
        //l.sendMsg(rcv, msg);
    }

    private void sendFile() {
        System.out.println("Indique o nome do ficheiro (sem o caminho)");
        String file = s.nextLine();
        System.out.println("Indique o caminho do ficheiro (absoluto e sem o nome):");
        String dir = s.nextLine();
        System.out.println("Indique o destinatario:");
        String rcv = s.nextLine();
        l.sendFile(file,dir, rcv);
    }

    private void rcvMsg() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void download() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void editChannel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createChannel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void deleteChannel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void stats() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
