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
    
    private String nome;
    private String newNome;
    private String password;
    private String descricao;

    public ChannelEditor(String newNome, String nome, String password, String descricao) {
        this.nome = nome;
        this.newNome = newNome;
        this.password = password;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }
     
    public String getNewNome() {
        return newNome;
    }

    public String getPassword() {
        return password;
    }

    public String getDescricao() {
        return descricao;
    }
    
    
}
