

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
    
    public static void printMenuVisitor(){
        System.out.println("\n=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("=====================================\n");
        System.out.println("  1 - LogIn");
        System.out.println("  2 - Regist new user"); // O registo só pode ser feito após a autorização do admin
        System.out.println("  3 - Newletter  (yet not implemented)");         // Esta opção passaria a ser "See Newletter"
        System.out.println("  4 - Last 3 itens sell (yet not implemented)");
        System.out.println("\n=====================================");
        System.out.println("    Exit (Press '0')  ");
        System.out.println("\n=====================================\n");
    }
    
    public static void printMenuUser(){
        System.out.println("\n=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("\n=====================================\n");
        System.out.println("  1 - Perfil");
        System.out.println("  2 - Messages");
        System.out.println("  3 - Item");
        System.out.println("  4 - Auction");
        System.out.println("  5 - See Newletter");
        System.out.println("  6 - Suspend Accout");
        System.out.println("  7 - Log off");
        System.out.println("\n=====================================\n");
    }
     
    public static void MenuUser(){
        
        int option;
        boolean continueMenu = true;
        
        while(continueMenu){
        
            printMenuUser();
            option = getOption(7);
            
            switch(option){
                case 1:
                    MenuUserPerfil();
                    break;
                case 7:
                    logoff();
                    continueMenu = false;
                    break;
                default:
                    System.out.println("[ERROR] Menu error");
                    break;
            }
        }
    }
    
    public static void printMenuUserPerfil(){
        System.out.println("\n=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("\n=====================================\n");
        System.out.println("  1 - See Data");
        System.out.println("  2 - Update Data");
        System.out.println("  3 - Check balance");
        System.out.println("  4 - Load balance");
        System.out.println("  5 - Return");
        System.out.println("\n=====================================\n");
    }
    
    public static void MenuUserPerfil(){
        
        int option;        
        boolean continueMenu = true;
        
        while(continueMenu){
            
            printMenuUserPerfil();
            option = getOption(5);
        
            switch(option){
                case 1:
                SeeData();
                break;
            case 2:
                UpdateData();
                break;
            case 3:
                CheckBalance();
                break;
            case 4:
                LoadBalance();
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
    
    public static void printMenuUserMessages(){
        System.out.println("\n=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("\n=====================================\n");
        System.out.println("  1 - Check messages");
        System.out.println("  2 - Send message");
        System.out.println("  3 - Return");
        System.out.println("\n=====================================\n");
    }
       
    public static void printMenuUserItem(){
        System.out.println("\n=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("\n=====================================\n");
        System.out.println("  1 - Create Item");
        System.out.println("  2 - Sell Item");
        System.out.println("  3 - Search Item");
        System.out.println("  4 - Follow Item");
        System.out.println("  5 - Check Follow Itens");
        System.out.println("  6 - Cancel Follow Item");
        System.out.println("  7 - Check Itens sell");
        System.out.println("  8 - See historic itens sell");
        System.out.println("  9 - See historic itens buy");
        System.out.println("  10 - Return");
        System.out.println("\n=====================================\n");
    }
        
    public static void printMenuUserAction(){
        System.out.println("\n=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("\n=====================================\n");
        System.out.println("  1 - Search Item");
        System.out.println("  2 - Bid Item");
        System.out.println("  3 - Report Item");
        System.out.println("  4 - Report Seller");
        System.out.println("  5 - Return");
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
    
    public static void SeeData(){
        System.out.println(User.SeePerfil());
    }
    
    public static void UpdateData(){
        
        String Address;
        
        System.out.print("\n Please insert the new Address: ");
        Address = sc.nextLine();        
        
        System.out.println(User.UpdatePerfil(Address));
    }
    
    public static void CheckBalance(){
        System.out.print(User.CheckBalance());
    }
    
    public static void LoadBalance(){
        
        double increment;
        
        System.out.print("\n Please insert the quantity you want load; ");
        increment = sc.nextLong();
        
        System.out.println(User.LoadBalance(increment));
    }
        
    public static void main(String[] args) {
        
        int option;
        
        boolean continueMenu = true;
        
        lookup();
        
        while(continueMenu){
         
            printMenuVisitor();
            option = getOption(3);
            
            switch(option){
                case 1:
                    login();
                    MenuUser();
                    break;
                case 2:
                    regist();
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
