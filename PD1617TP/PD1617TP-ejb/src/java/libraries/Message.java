

package libraries;

import java.util.Date;



public class Message {

    int id;
    String Message, Subject, Addressed, Sender;
    Date Date;

    public Message() {
    }
        
    public Message(int id, String Message, String Subject, String Addressed, String Sender, Date Date) {
        this.id = id;
        this.Message = Message;
        this.Subject = Subject;
        this.Addressed = Addressed;
        this.Sender = Sender;
        this.Date = Date;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return Message;
    }

    public String getSubject() {
        return Subject;
    }

    public String getAddressed() {
        return Addressed;
    }

    public String getSender() {
        return Sender;
    }

    public Date getDate() {
        return Date;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public void setAddressed(String Addressed) {
        this.Addressed = Addressed;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }

    
    
}
