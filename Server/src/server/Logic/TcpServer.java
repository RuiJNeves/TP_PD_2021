/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Logic;

import Features.User;
import java.util.List;

/**
 *
 * @author Hugo
 */
public class TcpServer {
    List<User> list ;
    
    public boolean removeUser(User user){
        return list.remove(user);
    }
    
    public void addUser(User user){
        list.add(user);
    }
    
}
