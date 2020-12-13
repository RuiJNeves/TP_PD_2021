/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.UI.Text;
import client.Logic.Logic;
import Features.Login;
import Features.Message;
import Helpers.RegisterRequest;
import client.Logic.Communication.ComsLogic;
import java.util.Scanner;

/**
 *
 * @author ruizi
 */
public class TextInterface {
    
    private final String[] MENU = {"Enviar Mensagem", "Enviar Ficheiro", "Receber Mensagen", "Download Ficheiro", "Criar Canal", 
                                    "Editar Canal", "Eliminar Canal", "Listar Canais/Utilizadores","Estatisticas", "Entrar num canal"};
    private final Scanner s;
    private Logic l;
    
    public TextInterface(ComsLogic cl){
        s = new Scanner(System.in);
        l = new Logic(cl);
    }
    
    public void run(){
        
        int opt;
        
        opt = writeMenu(new String[]{"Login", "Registo"});
        if(opt == 0)
            return;
        
        if(opt != 1){
            while(register())
                System.out.println("Registo falhado...");
        }
        
        while(login()){
            System.out.println("Login falhado...");
        }
  
        do{
            opt = writeMenu(MENU);
            computeOption(opt);
            
        }while(opt != 0);
        
        l.finish();
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
    
    private String readString(){
        return s.nextLine();
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
            case 10 -> entrarEmCanal();
            default -> {return;}
        }
    }

    private void sendMsg() {
        System.out.println("Indique o destinario:");
        String rcv = readString();
        System.out.println("Indique o conteúdo:");
        String msg =  readString();
        System.out.println("E para canl? (1 - Sim, 2 - Nao)");
        int c =  readInt();
        l.sendMessage(rcv, msg, (c==1));
    }

    private void sendFile() {
        System.out.println("Indique o nome do ficheiro (sem o caminho)");
        String file = s.nextLine();
        System.out.println("Indique o caminho do ficheiro (absoluto e sem o nome):");
        String dir = s.nextLine();
        System.out.println("Indique o destinatario:");
        String rcv = s.nextLine();
         System.out.println("E para canl? (1 - Sim, 2 - Nao)");
        int c =  readInt();
        l.sendFile(file,dir, rcv, (c==1));
    }

    private void rcvMsg() {
        System.out.println("Indique quantas mensagens deseja ver:");
        int n = readInt();
        System.out.println("Indique o nome do canal/utilizador de onde quer as mensagens:");
        String s = readString();
        System.out.println("è canal? (1 - Sim 2 - Nao)");
        int c = readInt();
        for(Message m : l.getMsgs(s,n, (c==1))){
            System.out.println(m.toString());
        }
    }

    private void download() {
        System.out.println("Indique o nome do ficheiro (sem o caminho)");
        String file = s.nextLine();
        System.out.println("Indique o caminho onde quer guardar o ficheiro (absoluto e sem o nome):");
        String dir = s.nextLine();
        System.out.println("Indique a origem (utilizador ou canal):");
        String rcv = s.nextLine();
        l.getFile(file,dir, rcv);
    }
    
    private void editChannel() {
        System.out.println("Indique o nome do canal a editar");
        String nome = readString();
        System.out.println("Indique a password");
        String cp = readString();
        System.out.println("Indique o novo nome ");
        String novo = readString();
        System.out.println("Indique a nova password");
        String novaPass = readString();
        System.out.println("Indique a nova descricao");
        String desc = readString();
        l.sendChannel(nome, cp, novo, novaPass, desc);
    }

    private void createChannel() {
        System.out.println("Indique o nome ");
        String nome = readString();
        System.out.println("Indique a password");
        String novaPass = readString();
        System.out.println("Indique a descricao");
        String desc = readString();
        l.sendChannel(null, null, nome, novaPass, desc);
    }

    private void deleteChannel() {
        System.out.println("Indique o nome ");
        String nome = readString();
        System.out.println("Indique a password");
        String pass = readString();
        l.sendChannel(nome, pass, null, null, null);
    }

    private void listInfo() {
        System.out.println("Informação sobre users e canais:");
        for(String str : l.getInfo()){
            System.out.println(str);
        }
    }

    private void stats() {
        
        String s = readString();
        System.out.println("Estatisitcas do canal: " + s);
        for(String str : l.getStats(s))
            System.out.println(str);
    }

    private boolean login() {
        System.out.println("Login");
        System.out.println("Username: ");
        String nome = readString();
        System.out.println("Password: ");
        String pass = readString();
        Login log = new Login(nome, pass);
        return l.login(log);
    }

    private boolean register() {
        System.out.println("Register");
        System.out.println("Username: ");
        String nome = readString();
        System.out.println("Password: ");
        String pass = readString();
        System.out.println("Email:");
        String email = readString();
        RegisterRequest reg = new RegisterRequest(nome, pass, email);
        return l.register(reg);
    }

    private void entrarEmCanal() {
        System.out.println("Indique o nome do canal:");
        String c = readString();
        System.out.println("Indique a password");
        String p = readString();
        l.enterChannel(c, p);
    }
}
