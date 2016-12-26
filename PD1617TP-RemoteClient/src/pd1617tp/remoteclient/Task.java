/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pd1617tp.remoteclient;

import pd1617tp.ResultMessage;
import static pd1617tp.remoteclient.PD1617TPRemoteClient.User;
import static pd1617tp.remoteclient.PD1617TPRemoteClient.sc;

/**
 *
 * @author Pedro Salgado
 */
public class Task {
    
    public static boolean regist() {
        
        String username;
        String password;
        
        System.out.print("Username: ");
        username = sc.nextLine();
        
        System.out.print("Password: ");
        password = sc.nextLine();
        
        ResultMessage result = User.Register(username, password);

        System.out.println("\n[DONE] " + result.Message() + "\n");
        
        if(ResultMessage.RegisterSucess == result)
            return true;
        else
            return false;
    }
    
    public static boolean login(){
       
        String username;
        String password;
        
        System.out.print("\n Username: ");
        username = sc.nextLine();
        
        System.out.print("\n Password: ");
        password = sc.nextLine();
        
        ResultMessage result = User.Login(username, password);

        System.out.println("\n[DONE] " + result.Message() + "\n");
        
         if(ResultMessage.LoginSucess == result)
            return true;
        else
            return false;
    }
    
    public static boolean logoff(){
        if(User.LogOff())
        {
            System.out.println("\n[DONE] LogOff success\n");
            return true;
        }
        else
        {
            System.out.println("\n[DONE] LogOff error\n");
            return false;
        }
    }
    
    public static void SeeData(){
        System.out.println(User.SeePerfil());
    }
    
    public static void UpdateData(){
        
        String Address;
        
        System.out.println("Please insert the new Address: ");
        Address = sc.nextLine();        
        
        System.out.println(User.UpdatePerfil(Address));
    }
    
    public static void CheckBalance(){
        System.out.print(User.CheckBalance());
    }
    
    public static void LoadBalance(){
        
        double increment;
        
        System.out.print("Please insert the quantity you want load: ");
        increment = sc.nextLong();
        
        System.out.println(User.LoadBalance(increment));
    }
    
}
