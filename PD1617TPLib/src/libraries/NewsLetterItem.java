
package libraries;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class NewsLetterItem implements Serializable{
    
    int Id;
    String Message;
    Date CreationDate;

    public NewsLetterItem(String Message) {
        
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        this.Id = 0;
        this.Message = Message;
        this.CreationDate = out;
    }

    public NewsLetterItem(int ID, String msg, Date date) {
        this.Id = ID;
        this.Message = msg;
        this.CreationDate = date;
    }

    public int getID() {
        return Id;
    }

    public String getMsg() {
        return Message;
    }

    public Date getDate() {
        return CreationDate;
    }    
}
