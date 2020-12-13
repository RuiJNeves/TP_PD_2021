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

    public File(String file, String dir) {
        this.file = file;
        this.dir = dir;
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
}
