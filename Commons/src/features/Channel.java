package Features;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class Channel implements Receiver{

    private final int IdCreator=0;
    private String nome;
    private String password;
    private String descricao;
    private static int users=0;
    private static int messages=0;
    private static int sharedFiles=0;

    
    /*Setters*/
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static void setUsers(int users) {
        Channel.users = users;
    }

    public static void setMessages(int messages) {
        Channel.messages = messages;
    }

    public static void setSharedFiles(int sharedFiles) {
        Channel.sharedFiles = sharedFiles;
    }

    
    
    /*Getters*/
    public int getIdCreator() {
        return IdCreator;
    }

    public String getNome() {
        return nome;
    }

    public String getPassword() {
        return password;
    }

    public String getDescricao() {
        return descricao;
    }

    public static int getUsers() {
        return users;
    }

    public static int getMessages() {
        return messages;
    }

    public static int getSharedFiles() {
        return sharedFiles;
    }
    
    
    
    @Override
    public void send(MidiMessage message, long timeStamp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
