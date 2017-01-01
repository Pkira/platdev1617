
package libraries;

import java.util.Date;


public class Notification {
    
    String FromUser;
    String Message;
    Date CreationDate;
    boolean Resolved;

    public Notification(String FromUser, String Message, Date CreationDate) {
        this.FromUser = FromUser;
        this.Message = Message;
        this.CreationDate = CreationDate;
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
