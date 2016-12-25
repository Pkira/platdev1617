

package libraries;

import java.util.Date;


public class NewsLetter {
    
    int ID;
    String msg;
    Date date;

    public NewsLetter() {
    }

    public NewsLetter(int ID, String msg, Date date) {
        this.ID = ID;
        this.msg = msg;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public String getMsg() {
        return msg;
    }

    public Date getDate() {
        return date;
    }    
    
}
