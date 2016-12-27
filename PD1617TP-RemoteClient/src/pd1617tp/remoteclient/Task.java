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
import libraries.ResultMessage;
import static pd1617tp.remoteclient.PD1617TPRemoteClient.Newsletter;
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
       
        NewsLetter newsletter = Newsletter.GetNewsLetter();
        
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
        
        ArrayList<Messages> msg = User.CheckMessage();
        
        if(msg == null || msg.isEmpty())
            System.out.println("\n[INFO] No messages available to read");
        else
        {
            System.out.println("\nYou have " + msg.size() + " messages in your message box\n");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            if(msg.size() <= 3)
                for(int i = 0; i < msg.size(); i++)
                {
                    Messages aux = (Messages) msg.get(i);
                    System.out.println("Message Send by : " + aux.getSender());
                    System.out.println("Message Subject: " + aux.getSubject());
                    System.out.println("Message : " + aux.getMessage());
                    System.out.println("Date : " + dateFormat.format(aux.getDate()) + "\n");        
                }
            else
            {
                System.out.println("Here are your last 3 messages\n");
                for(int i = msg.size() - 3; i < msg.size(); i++)
                {
                    Messages aux = (Messages) msg.get(i);
                    System.out.println("Message Send by : " + aux.getSender());
                    System.out.println("Message Subject: " + aux.getSubject());
                    System.out.println("Message : " + aux.getMessage());
                    System.out.println("Date : " + dateFormat.format(aux.getDate()) + "\n");
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
