/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

/**
 *
 * @author ruizi
 */
public class ServerResponse {
    private boolean result;
    private String msg;
    
    public ServerResponse(boolean r, String m){
        result = r;
        msg = m;
    }

    public boolean isResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }
    
}
