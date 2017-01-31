/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pd1617tp.remoteclient;

/**
 *
 * @author Pedro Salgado
 */
public class Menu {
    
    public static void printMenuVisitor(){
        System.out.println("\n=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("=====================================\n");
        System.out.println("  1 - LogIn");
        System.out.println("  2 - Regist new user"); 
        System.out.println("  3 - Show Newletter");         
        System.out.println("  4 - Last 3 itens sell (Not implemented yet)");
        System.out.println("  5 - Account Reactivation");
        System.out.println("=====================================");
        System.out.println("    Exit (Press '0')  ");
        System.out.println("=====================================");
    }
    
    public static void printMenuUser(){
        System.out.println("=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("=====================================");
        System.out.println("  1 - Profile");
        System.out.println("  2 - Messages");
        System.out.println("  3 - Item");
        System.out.println("  4 - Auction                 (Not implemented yet)");
        System.out.println("  5 - Show Newletter");
        System.out.println("  6 - Suspend Accout          (Not implemented yet)");
        System.out.println("  7 - Log off");
        System.out.println("=====================================");
    }
    
    public static void printMenuAdmin(){
        System.out.println("=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("=====================================");
        System.out.println("  1 - Notifications         (Not implemented yet)");
        System.out.println("  2 - Activate Account");
        System.out.println("  3 - Re-Activate Account");
        System.out.println("  4 - Suspend Account");
        System.out.println("  5 - See user profile");
        System.out.println("  6 - Search Items          (Not implemented yet)");
        System.out.println("  7 - Cancel Items          (Not implemented yet)");
        System.out.println("  8 - Add new Category      (Not implemented yet)");
        System.out.println("  9 - Edit Category         (Not implemented yet)");
        System.out.println("  10 - Change User Password");
        System.out.println("  11 - Show Newletter");
        System.out.println("  12 - Log off");
        System.out.println("  13 - Save Server State");
        System.out.println("=====================================");
    }
    
    public static void printMenuUserProfile(){
        System.out.println("=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("=====================================");
        System.out.println("  1 - See Data");
        System.out.println("  2 - Update Data");
        System.out.println("  3 - Check balance");
        System.out.println("  4 - Load balance");
        System.out.println("  5 - Return");
        System.out.println("=====================================");
    }
    
    public static void printMenuUserMessages(){
        System.out.println("=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("=====================================");
        System.out.println("  1 - Check messages");
        System.out.println("  2 - Send message");
        System.out.println("  3 - Return");
        System.out.println("=====================================");
    }
       
    public static void printMenuUserItem(){
        System.out.println("=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("=====================================");
        System.out.println("  1 - Create Item");
        System.out.println("  2 - Sell Item");
        System.out.println("  3 - Search Item");
        System.out.println("  4 - Follow Item             (Not implemented yet)");
        System.out.println("  5 - Check Follow Itens");
        System.out.println("  6 - Cancel Follow Item      (Not implemented yet)");
        System.out.println("  7 - Check Itens in sell");
        System.out.println("  8 - See historic itens sell (Not implemented yet)");
        System.out.println("  9 - See historic itens buy  (Not implemented yet)");
        System.out.println("  10 - Return");
        System.out.println("=====================================");
    }
        
    public static void printMenuUserAuction(){
        System.out.println("=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("=====================================");
        System.out.println("  1 - Search Item");
        System.out.println("  2 - Bid Item");
        System.out.println("  3 - Report Item             (Not implemented yet)");
        System.out.println("  4 - Report Seller           (Not implemented yet)");
        System.out.println("  5 - Return");
        System.out.println("=====================================");
    }
}
