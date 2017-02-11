/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Item;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import java.util.List;
import entities.User;
import facades.IItem;
import facades.IUser;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import utils.ResultMessage;


@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private IItem itemFacade;

    @EJB
    private IUser userFacade;
    
    
    private long userid;
    private String username;
    private String password;
    private String newpassword;
    private String address;
    private boolean isLogged;
    private boolean isAdmin;
    private double balance;
    private int totaluseritems;
    private int totaluseritemsprice;
    private List<Item> useritems;


    public UserController() {
        this.userid = 0;
        this.username = null;
        this.password = null;
        this.address = null;
        this.isLogged = false;
        this.balance = 0;
        this.isAdmin = false;
    }
    
    public List<User> getAllUsers()
    {
        return userFacade.getAll();
    }
    
    public void createNew(){
        
        userFacade.RegisterUser(username, password, address);       
    }
    
    public void login()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        ResultMessage result = userFacade.Login(username, password);
        
        if(result.equals(ResultMessage.LoginSucess))
            setIsLogged(true);
        else
        {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
            return;
        }
        
        User user = userFacade.SeeProfile();
        
        this.userid = user.getId();
        this.address = user.getAddress();
        this.balance = user.getBalance();
        this.useritems = itemFacade.UserItems(userid);
        this.isAdmin = false;
        
        if(this.username.equals("admin"))
        {
            this.isAdmin = true;
        }
    }
    
    public String logoff()
    {
        boolean result = userFacade.LogOff();
        
        if(result)
            setIsLogged(false);
        
        return "index.xhtml";
    }
    
    public void updateProfile()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(newpassword.isEmpty())
            newpassword = password;
        
        ResultMessage result = userFacade.UpdateProfile(address, newpassword, password);
        
        if(result != ResultMessage.UpdateProfileValid)
        {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
            return;
        }
        else
        {
            userFacade.LoadBalance(balance);
        
            this.balance = userFacade.SeeProfile().getBalance();
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
        }
        

    }
    
    public String FollowItem(long ItemId){
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResultMessage result = null;
        try {
            result = itemFacade.FollowItem( ItemId, userid);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR ", null));
        }
        if(result == ResultMessage.FollowItemSucess)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
        
        return "UserItems.xhtml";
    }
    
    public String CancelFollowItem(long ItemId){
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResultMessage result = null;
        try {
            result = itemFacade.CancelFollowItem( ItemId, userid);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", null));
        }
        if(result == ResultMessage.CancelFollowItemSucess)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result.Message(), null));
        else
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result.Message(), null));
        
        return "UserItemsFollowing.xhtml";
    }
    
    public List<Item> getFollowingItems(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<Item> items = null;
        
        try {
            items = itemFacade.FollowItens(userid);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error getting following items ", null));
        }
        
        return items;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public int getTotaluseritems() {
        this.useritems = itemFacade.UserItems(userid);
        totaluseritems = this.useritems.size();
        return totaluseritems;
    }

    public void setTotaluseritems(int totaluseritems) {
        this.totaluseritems = totaluseritems;
    }

    public List<Item> getUseritems() {
        return useritems;
    }

    public void setUseritems(List<Item> useritems) {
        this.useritems = useritems;
    }

    public int getTotaluseritemsprice() {
        totaluseritemsprice = 0;
        this.useritems = itemFacade.UserItems(userid);
        
        for(Item i : this.useritems)
            totaluseritemsprice += i.getStartprice();
        
        return totaluseritemsprice;
    }

    public void setTotaluseritemsprice(int totaluseritemsprice) {
        this.totaluseritemsprice = totaluseritemsprice;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    
    
}
