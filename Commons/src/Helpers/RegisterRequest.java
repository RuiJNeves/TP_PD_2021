/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import interfaces.IRequest;

/**
 *
 * @author ruizi
 */
public class RegisterRequest implements IRequest{
    
    private String nome;
    private String email;
    private String pass;
    
    public RegisterRequest(String nome, String pass, String email) {
        this.nome = nome;
        this.email = email;
        this.pass = pass;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
    
    
}
