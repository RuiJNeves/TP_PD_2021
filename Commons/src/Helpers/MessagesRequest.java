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
    private boolean fromChannel;
    private String me;
    public MessagesRequest(String org, int numMsgs, boolean channel, String me){
        this.me = me;
        this.org = org;
        fromChannel = channel;
        this.numMsgs = numMsgs;
    }

    public String getOrg() {
        return org;
    }
    
    public String getMe() {
        return me;
    }

    public int getNumMsgs() {
        return numMsgs;
    }
    
    public boolean isFromChannel(){
        return fromChannel;
    }
    
}
