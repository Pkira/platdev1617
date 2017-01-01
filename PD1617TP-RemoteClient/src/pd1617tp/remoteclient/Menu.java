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
        System.out.println("  4 - Last 3 itens sell (yet not implemented)");
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
        System.out.println("  4 - Auction                 (yet not implemented)");
        System.out.println("  5 - Show Newletter");
        System.out.println("  6 - Suspend Accout          (yet not implemented)");
        System.out.println("  7 - Log off");
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
        System.out.println("  4 - Follow Item             (yet not implemented)");
        System.out.println("  5 - Check Follow Itens      (yet not implemented)");
        System.out.println("  6 - Cancel Follow Item      (yet not implemented)");
        System.out.println("  7 - Check Itens sell        (yet not implemented)");
        System.out.println("  8 - See historic itens sell (yet not implemented)");
        System.out.println("  9 - See historic itens buy  (yet not implemented)");
        System.out.println("  10 - Return");
        System.out.println("=====================================");
    }
        
    public static void printMenuUserAuction(){
        System.out.println("=====================================");
        System.out.println("    Auction System Remote Client  ");
        System.out.println("=====================================");
        System.out.println("  1 - Search Item");
        System.out.println("  2 - Bid Item");
        System.out.println("  3 - Report Item             (yet not implemented)");
        System.out.println("  4 - Report Seller           (yet not implemented)");
        System.out.println("  5 - Return");
        System.out.println("=====================================");
    }
}
