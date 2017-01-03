
package libraries;

import java.util.Date;


public class Notification {
    
    String FromUser;
    String Message;
    Date CreationDate;
    boolean Resolved;
    long id;

    public Notification(String FromUser, String Message, long id) {
        this.id = id;
        this.FromUser = FromUser;
        this.Message = Message;
        this.CreationDate = new Date();
        this.Resolved = false;
    }

    public String getFromUser() {
        return FromUser;
    }

    public void setFromUser(String FromUser) {
        this.FromUser = FromUser;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date CreationDate) {
        this.CreationDate = CreationDate;
    }

    public boolean isResolved() {
        return Resolved;
    }

    public void setResolved(boolean Resolved) {
        this.Resolved = Resolved;
    }
    
    
}
