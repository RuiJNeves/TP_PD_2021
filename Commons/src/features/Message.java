package Features;

import java.util.Date;


public class Message {
    
    private String usernameSend;
    private String usernameRecive;
    private Date date;
    private String message;

    public Message(String snd, String rcv, String msg){
        usernameSend = snd;
        usernameRecive = rcv;
        message = msg;
    }
    
    public Message(){
        
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
}
