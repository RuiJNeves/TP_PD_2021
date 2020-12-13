package Features;

import interfaces.IReceiver;


public class User implements IReceiver{
    
    private int id;
    private String nome;
    private String password;
    private String email;
    
    /*Setters*/
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setEmail(String mail) {
        this.email = mail;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    /*Getters*/
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
}
