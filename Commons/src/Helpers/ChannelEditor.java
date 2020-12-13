/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import interfaces.ISendable;

/**
 *
 * @author ruizi
 */
public class ChannelEditor implements ISendable{
    private String creator;
    private String nome;
    private String currentPass;
    private String newNome;
    private String password;
    private String descricao;

    public ChannelEditor(String nome, String currentPassword, String newNome, String password, String descricao, String creator) {
        this.nome = nome;
        this.currentPass = currentPassword;
        this.newNome = newNome;
        this.password = password;
        this.descricao = descricao;
        this.creator = creator;
    }

    public String getNome() {
        return nome;
    }
     
    public String getNewNome() {
        return newNome;
    }
    
    public String getCurrentPassword() {
        return currentPass;
    }

    public String getPassword() {
        return password;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public String getCreator(){
        return creator;
    }
    
    
}
