/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Features;

/**
 *
 * @author ruizi
 */
public class Login {
    
    private final String nome;
    private final String password;
    
    public Login(String nome, String password){
        this.nome = nome;
        this.password = password;
    }
    
    public String getNome(){
        return nome;
    }
    
    public String getPass(){
        return password;
    }
    
}
