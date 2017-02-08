/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import java.util.List;
import entities.User;
import facades.IUser;
import utils.ResultMessage;


@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private IUser userFacade;
    
    private long userid;
    private String username;
    private String password;
    private String newpassword;
    private String address;
    private boolean isLogged;
    private double balance;

    public UserController() {
        this.userid = 0;
        this.username = null;
        this.password = null;
        this.address = null;
        this.isLogged = false;
        this.balance = 0;
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
        ResultMessage result = userFacade.Login(username, password);
        
        if(result.equals(ResultMessage.LoginSucess) || result.equals(ResultMessage.LoginAllreadyLogged))
            setIsLogged(true);
        
        User user = userFacade.SeeProfile();
        
        this.userid = user.getId();
        this.address = user.getAddress();
        this.balance = user.getBalance();
    }
    
    public void logoff()
    {
        boolean result = userFacade.LogOff();
        
        if(result)
            setIsLogged(false);
    }
    
    public void updateProfile()
    {
        if(newpassword.isEmpty())
            newpassword = password;
        
        ResultMessage result = userFacade.UpdateProfile(address, newpassword, password);
        userFacade.LoadBalance(balance);
        
        this.balance = userFacade.SeeProfile().getBalance();
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
    
    
}
