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
public class MessagesRequest implements IRequest{
    private String org;
    private int numMsgs;
    
    public MessagesRequest(String org, int numMsgs){
        this.org = org;
        this.numMsgs = numMsgs;
    }

    public String getOrg() {
        return org;
    }

    public int getNumMsgs() {
        return numMsgs;
    }
    
    
}
