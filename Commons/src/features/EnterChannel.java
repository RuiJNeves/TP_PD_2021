/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Features;

import interfaces.ISendable;

/**
 *
 * @author ruizi
 */
public class EnterChannel implements ISendable {
    
    String channel;
    String pass;
    String user;
    
    public EnterChannel(String user, String pass, String channel){
        this.user = user;
        this.pass = pass;
        this.channel = channel;
    }
    
    public String getChannel() {return channel;}
    public String getUser(){return user;}
    public String getPass(){return pass;}
}
