

package pd1617tp.remoteclient;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import libraries.ResultMessage;
import pd1617tp.IAdmin;
import pd1617tp.IAuction;
import pd1617tp.INewsLetter;
import pd1617tp.IUser;
import pd1617tp.IVisitor;
import pd1617tp.IItem;


public class PD1617TPRemoteClient {

    public static IUser User;
    public static INewsLetter Newsletter;
    public static IVisitor Visitor;
    public static IAdmin Admin;
    public static IItem Item;
    public static IAuction Auction;
    
    static Scanner sc = new Scanner(System.in);
  
    static void lookup() {
        
        InitialContext ctx = null;
        Properties prop = new Properties();
        
        prop.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        prop.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        prop.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        prop.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        prop.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        try {
            ctx = new InitialContext(prop);
        }
        catch (NamingException e) {
                System.out.println(e.getMessage());
                System.exit(1);
        }
        System.out.println("[Info] InitialContext sucefull created.");

        String SessionUserBean = "java:global/PD1617TP/PD1617TP-ejb/UserBean!pd1617tp.IUser";
        String NewsLetterBean = "java:global/PD1617TP/PD1617TP-ejb/NewsLetterBean!pd1617tp.INewsLetter";
        String SessionAdminBean = "java:global/PD1617TP/PD1617TP-ejb/AdminBean!pd1617tp.IAdmin";
        String SessionVisitorBean = "java:global/PD1617TP/PD1617TP-ejb/VisitorBean!pd1617tp.IVisitor";
        String ItemBean = "java:global/PD1617TP/PD1617TP-ejb/ItemBean!pd1617tp.IItem";
        String AuctionBean = "java:global/PD1617TP/PD1617TP-ejb/AuctionBean!pd1617tp.IAuction";

        try {
            System.out.println("[Info] Starting lookup");
            
            Object lookupSessionUser = ctx.lookup(SessionUserBean);
            User =(IUser)lookupSessionUser;
            
            Object lookupNewsLetter = ctx.lookup(NewsLetterBean);
            Newsletter =(INewsLetter)lookupNewsLetter;
            
            Object lookupAdmin = ctx.lookup(SessionAdminBean);
            Admin =(IAdmin)lookupAdmin;
            
            Object lookupVisitor = ctx.lookup(SessionVisitorBean);
            Visitor =(IVisitor)lookupVisitor;
            
            Object lookupItem = ctx.lookup(ItemBean);
            Item =(IItem)lookupItem;
            
            Object lookupAuction = ctx.lookup(AuctionBean);
            Auction =(IAuction)lookupAuction;
        }
        catch (NamingException e) {
            
            System.out.println("[Error] Lookup ended with the following error:");
            
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
       System.out.println("[Info] Lookup successfully completed");
    }
    
    public static void main(String[] args) {
        
        int option;
        
        boolean continueMenu = true;
        
        lookup();
        
        try
        {
        
            while(continueMenu){

                Menu.printMenuVisitor();
                option = getOption(3);

                switch(option){
                    case 1:
                        if(Task.login())
                        {
                            MenuUser();
                            break;
                        }
                        else
                            continue;

                    case 2:
                        Task.regist();
                        break;

                    case 3:
                        Task.ShowNewsLetter();
                        break;

                    case 0:
                        continueMenu = false;
                        break;
                    default:
                        System.out.println("[ERROR] Menu error");
                        break;
                }
            }

        }
        catch (Exception ex) {
            
            System.out.println("[Error] Global exception generated while running application client. ErrorMessage:" + ex.getMessage());           
        }

        try
        {
            System.out.println("[INFO] Trying to log off client before exit...");
            Task.logoff();
            System.out.println("[DONE] Client successfully loged off");
        }
        catch (Exception ex2) {
            System.out.println("[Error] Unable to log off user, possible internal error in server. ErrorMessage:" + ex2.getMessage());
        }        
           
        return;
    }
     
    public static void MenuUser(){
        
        int option;
        boolean continueMenu = true;
        
        while(continueMenu){
        
            Menu.printMenuUser();
            option = getOption(7);
            
            switch(option){
                case 1:
                    MenuUserProfile();
                    break;
                case 2:
                    MenuUserMessage();
                    break;
                case 3:
                    MenuUserItem();
                    break;
                case 5:
                    Task.ShowNewsLetter();
                    break;
                case 7:
                    Task.logoff();
                    continueMenu = false;
                    break;
                default:
                    System.out.println("[ERROR] Menu error");
                    break;
            }
        }
    }
    
    public static void MenuUserProfile(){
        
        int option;        
        boolean continueMenu = true;
        
        while(continueMenu){
            
            Menu.printMenuUserProfile();
            option = getOption(5);
        
            switch(option){
                case 1:
                Task.SeeData();
                break;
            case 2:
                Task.UpdateData();
                break;
            case 3:
                Task.CheckBalance();
                break;
            case 4:
                Task.LoadBalance();
                break;
            case 5:
                continueMenu = false;
                break;
            default:
                System.out.println("[ERROR] Menu error");
                break;
            }
        }    
    }
    
    public static void MenuUserMessage(){
        
        int option;        
        boolean continueMenu = true;
        
        while(continueMenu){
            
            Menu.printMenuUserMessages();
            option = getOption(5);
        
            switch(option){
                case 1:
                    Task.CheckMessage();
                    break;
                case 2:
                    Task.SendMessage();
                    break;
                case 3:
                    continueMenu = false;
                    break;
                default:
                    System.out.println("[ERROR] Menu error");
                    break;
            }
        }    
    }
   
        public static void MenuUserItem(){
        
        int option;        
        boolean continueMenu = true;
        
        while(continueMenu){
            
            Menu.printMenuUserItem();
            option = getOption(10);
        
            switch(option){
                case 1:
                    Task.CreateItem();
                    break;
                case 2:
                    Task.CreateAuction();
                    break;
                case 3:
                    Task.SearchItem();
                    break;
                case 10:
                    continueMenu = false;
                    break;
                default:
                    System.out.println("[ERROR] Menu error");
                    break;
            }
        }    
    }

    public static void MenuUserAuction(){
        
        int option;        
        boolean continueMenu = true;
        
        while(continueMenu){
            
            Menu.printMenuUserAuction();
            option = getOption(5);
        
            switch(option){
                case 5:
                    continueMenu = false;
                    break;
                default:
                    System.out.println("[ERROR] Menu error");
                    break;
            }
        }    
    }    
        
    public static int getOption(int max){
        
        int option;
        String texto;
        
        while(true){
            try {
                System.out.print("Option: ");
                texto = sc.nextLine();
                option = Integer.parseInt(texto);
                if((option >= 0) && (option <= max))
                    return option;
                System.out.println("[Error] Wrong option pressed");
            }
            catch(NumberFormatException e){
               System.out.println("[Error] problem reeding from keyboard");
            }
        }
    }
        
}
