package Features;

import java.lang.String;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class User implements Receiver{
    
    private final int Id=0;
    private String nome;
    private String password;
    private int token;
    private String imagePath;
    
    
    /*Setters*/
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    
    /*Getters*/
    public int getId() {
        return Id;
    }

    public String getNome() {
        return nome;
    }

    public String getPassword() {
        return password;
    }

    public int getToken() {
        return token;
    }

    public String getImagePath() {
        return imagePath;
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
