

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
    
    public static void main(String[] args) {
        
        int option;
        
        boolean continueMenu = true;
        
        lookup();
        
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
                    if(Task.regist())
                    {
                        MenuUser();
                        break;
                    }
                    else
                        continue;
                case 0:
                    continueMenu = false;
                    break;
                default:
                    System.out.println("[ERROR] Menu error");
                    break;
            }
        }        
           
           Task.logoff();
           System.out.println("\n[DONE] Log Off");
    }
     
    public static void MenuUser(){
        
        int option;
        boolean continueMenu = true;
        
        while(continueMenu){
        
            Menu.printMenuUser();
            option = getOption(7);
            
            switch(option){
                case 1:
                    MenuUserPerfil();
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
    
    public static void MenuUserPerfil(){
        
        int option;        
        boolean continueMenu = true;
        
        while(continueMenu){
            
            Menu.printMenuUserPerfil();
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
