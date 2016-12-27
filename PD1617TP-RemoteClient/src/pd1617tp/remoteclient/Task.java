/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pd1617tp.remoteclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import libraries.Messages;
import libraries.NewsLetter;
import libraries.NewsLetterItem;
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
    
    public static void ShowNewsLetter(){
       
        NewsLetter newsletter = User.GetNewsletter();
        
        if(newsletter != null)
        {
            ArrayList<NewsLetterItem> news = newsletter.getNewsList();
            if(!news.isEmpty())
            {
                System.out.println("\n[Newsletter]\n");
                for(NewsLetterItem item : news)
                {
                    System.out.println(item.getMsg());
                }
                 
            }
            else
                System.out.println("\n[Newsletter] is empty\n");
        }
        else
            System.out.println("[ERROR] Error getting newsletter");
 
    }
    
    public static void CheckMessage(){
        
        if(User.CheckMessage() == null || User.CheckMessage().size() <= 0)
            System.out.println("\n[INFO] No messages available to read");
        else
        {
            System.out.println("\nYou have " + User.CheckMessage().size() + " messages in your message box");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            if(User.CheckMessage().size() <= 3)
                for(int i = 0; i <= User.CheckMessage().size(); i++)
                {
                    Messages msg = (Messages) User.CheckMessage().get(i);
                    System.out.println("Message Send by : " + msg.getSender());
                    System.out.println("Message Subject: " + msg.getSubject());
                    System.out.println("Message : " + msg.getMessage());
                    System.out.println("Date : " + dateFormat.format(msg.getDate()) + "\n");        
                }
            else
            {
                System.out.println("Here are your last 3 messages");
                for(int i = User.CheckMessage().size() - 3; i <= User.CheckMessage().size(); i++)
                {
                    Messages msg = (Messages) User.CheckMessage().get(i);
                    System.out.println("Message Send by : " + msg.getSender());
                    System.out.println("Message Subject: " + msg.getSubject());
                    System.out.println("Message : " + msg.getMessage());
                    System.out.println("Date : " + dateFormat.format(msg.getDate()) + "\n");
                }
            }
        }
    }
    
    public static void SendMessage(){
        
        String addressed, subject, message;
        
        System.out.print("Send to: ");
        addressed = sc.nextLine();
        
        System.out.print("\nSubject: ");
        subject = sc.nextLine();
        
        System.out.print("\nMessage: ");
        message = sc.nextLine();
        
        System.out.println(User.SendMessage(addressed, subject, message));
                
    }
}
