package Features;

import interfaces.ISendable;
import java.util.Date;


public class Message implements ISendable {
    private int id;
    private String usernameSend;
    private String usernameRecive;
    private Date date;
    private String message;
    private boolean toChannel;
    
    public Message(int id, String snd, String rcv, String msg,  boolean channel){
        toChannel = channel;
        this.id = id;
        usernameSend = snd;
        usernameRecive = rcv;
        message = msg;
    }
    
    
    public Message(String snd, String rcv, String msg, boolean channel){
        id = 0;
        toChannel = channel;
        usernameSend = snd;
        usernameRecive = rcv;
        message = msg;
    }
    
    /*Setters*/
    public void setUsernameSend(String usernameSend) {
        this.usernameSend = usernameSend;
    }

    public void setUsernameRecive(String usernameRecive) {
        this.usernameRecive = usernameRecive;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    
    /*Getters*/
    public String getUsernameSend() {
        return usernameSend;
    }

    public String getUsernameRecive() {
        return usernameRecive;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
    
    public int getId() {
        return id;
    }
    
    public boolean isToChannel() {
        return toChannel;
    }

    @Override
    public String toString() {
        return "De " + usernameSend + "\n"+ "A " + date.toString() + "\n"+message;
    }
    
    
}
