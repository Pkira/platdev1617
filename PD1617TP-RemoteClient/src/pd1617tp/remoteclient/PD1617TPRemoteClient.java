

package pd1617tp.remoteclient;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import pd1617tp.ISessionUser;
import pd1617tp.ResultMessage;


public class PD1617TPRemoteClient {

    static ISessionUser User;
    
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

        String rsfull_class_name = "java:global/PD1617TP/PD1617TP-ejb/SessionUser!pd1617tp.ISessionUser";

        try {
            System.out.println("[Info] Starting lookup");
            Object x = ctx.lookup(rsfull_class_name);
            User =(ISessionUser)x;
        }
        catch (NamingException e) {
            
            System.out.println("[Error] Lookup ended with the following error:");
            
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
       System.out.println("[Info] Lookup successfully completed");
    }
    
    public static void printMenu(){
        System.out.println("\n=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("\n=====================================\n");
        System.out.println("  1 - LogIn");
        System.out.println("  2 - Regist new user");
        System.out.println("  3 - Log off");
        System.out.println("\n=====================================");
        System.out.println("    Exit (Press '0')  ");
        System.out.println("\n=====================================\n");
    }
    
    public static int getOption(int max){
        
        int option;
        String texto;
        
        while(true){
            try {
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
    
    public static void regist() {
        
        String username;
        String password;
        
        System.out.print("\n Username: ");
        username = sc.nextLine();
        
        System.out.print("\n Password: ");
        password = sc.nextLine();
        
        ResultMessage result = User.Register(username, password);

        System.out.println("[DONE] " + result.Message());
    }
    

    
    public static void login(){
       
        String username;
        String password;
        
        System.out.print("\n Username: ");
        username = sc.nextLine();
        
        System.out.print("\n Password: ");
        password = sc.nextLine();
        
        ResultMessage result = User.Login(username, password);

        System.out.println("[DONE] " + result.Message());
    }
    
    
    public static void logoff(){
        if(User.LogOff())
            System.out.println("[DONE] LogOff success");
        else
            System.out.println("[DONE] LogOff error");
    }
    
    public static void main(String[] args) {
        
        int option;
        
        boolean continueMenu = true;
        
        lookup();
        
        while(continueMenu){
         
            printMenu();
            option = getOption(3);
            
            switch(option){
                case 1:
                    login();
                    break;
                case 2:
                    regist();
                    break;            
                case 3:
                    logoff();
                    break;
                case 0:
                    continueMenu = false;
                    break;
                default:
                    System.out.println("[ERROR] Menu error");
                    break;
            }
        }        
           
           logoff();
           System.out.println("\n[DONE] Log Off");
    }
    
}
