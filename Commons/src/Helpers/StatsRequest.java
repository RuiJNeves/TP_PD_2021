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
public class StatsRequest implements IRequest{
    private String channelName;
    
    public StatsRequest(String nome){
        channelName = nome;
    }
    
    public String getChannelName(){return channelName;}
}
