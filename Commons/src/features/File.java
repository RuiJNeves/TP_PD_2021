/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Features;

import interfaces.ISendable;

/**
 *
 * @author Hugo
 */
public class File implements ISendable{

    int id;
    String file;
    String dir;
    boolean toChannel;
    String snd;
    String rcv;
    boolean sending;
<<<<<<< HEAD
    
    public File(String file, String dir, boolean toChannel, String snd, String rcv, boolean sending){
=======

    public File(String file, String dir, boolean toChannel, String snd, String rcv, boolean sending) {
>>>>>>> d75c692db45c63144c40e29902ec281b7c4a5b93
        this.file = file;
        this.dir = dir;
        this.toChannel = toChannel;
        this.snd = snd;
        this.rcv = rcv;
        this.sending = sending;
<<<<<<< HEAD
=======
    }
    
    public boolean isSending() {
        return sending;
>>>>>>> d75c692db45c63144c40e29902ec281b7c4a5b93
    }
    
    public boolean isSending(){
        return sending;
    }
    
    public String getSnd() {
        return snd;
    }

    public String getRcv() {
        return rcv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }
    
    public String getDir() {
        return dir;
    }
    
    public boolean isToChannel() {
        return toChannel;
    }
}
