/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.Logic;

import Features.*;
import Helpers.*;
import client.Logic.Communication.*;
import java.io.File;
import java.util.List;

/**
 *
 * @author ruizi
 */
public class Logic {

    private User currentUser;
    private ComsLogic cl;
    private boolean isLogged;

    public Logic(ComsLogic cl) {
        this.cl = cl;
        isLogged = false;
    }

    public void sendMsg(String rcv, String msg, boolean toChannel) {
        Message m;
        if (isLogged) {
            m = new Message(currentUser.getNome(), rcv, msg, toChannel);
        }

    }

    public void sendFile(String file, String dir, String s, boolean toChannel) {
        File f = new File(dir);
        cl.sendFile(file, f, currentUser.getNome(), toChannel, s);
    }

    public boolean login(Login log) {
        User r = cl.login(log);
        if (r.getNome() != null) {
            currentUser = r;
        }
        return r.getNome() != null;
    }

    public void sendMessage(String dest, String msg, boolean toChannel) {
        Message m = new Message(currentUser.getNome(), dest, msg, toChannel);
        cl.sendMsg(m);

    }

    public List<Message> getMsgs(String s, int n, boolean c) {
        MessagesRequest req = new MessagesRequest(s, n,c, currentUser.getNome());
        return cl.sendMsgRequest(req);
    }

    public void getFile(String file, String dir, String snd) {
        File f = new File(dir);
        cl.getFile(file, f, snd);
    }

    public List<String> getStats(String channel) {
        StatsRequest req = new StatsRequest(channel);
        return cl.getStats(req);
    }

    public List<String> getInfo() {
        InfoRequest req = new InfoRequest();
        return cl.getInfo(req);
    }

    public void sendChannel(String n, String cp, String nn, String p, String d) {
        ChannelEditor edt = new ChannelEditor(n, cp, nn, p, d, currentUser.getNome());
        cl.sendChannel(edt);
    }

    public boolean register(RegisterRequest reg) {
        
        return cl.register(reg);
    }
}
