package Features;

import interfaces.IReceiver;


public class Channel implements IReceiver{

    private int IdCreator;
    private String nome;
    private String password;
    private String descricao;
    private int id;

    
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
    
    public void setIdCreator(int id) {
        IdCreator = id;
    }
    
     public void setId(int id) {
        this.id = id;
    }

    
    /*Getters*/
    public int getIdCreator() {
        return IdCreator;
    }
    
     public int getId() {
        return id;
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
}
