
package libraries;

// persistence DB

import java.util.ArrayList;

public class FactoryDB {
    
    private static ArrayList<User> UserList;
    

    public FactoryDB()
    {
        UserList = new ArrayList<User>();
        
        User admin = new User("admin","admin","");
        UserList.add(admin);
    }
       
    public static boolean UserRegister (User NewUser)
    {
        UserList.add(NewUser);
        return true;
    }
    
    public static User UserLogin (String Username, String Password)
    {
        for (User user : UserList) {
            if (user.Username.equals(Username) && user.Password.equals(Password)) {
              return user;
            }
        }
        return null;
    }
    
    public static User GetUserbyUsername (String Username)
    {
        for (User user : UserList) {
            if (user.Username.equals(Username)) {
              return user;
            }
        }
        
        return null;
    }
    
}
