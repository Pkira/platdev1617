/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Message;
import entities.User;
import java.util.List;
import javax.ejb.Local;
import utils.ResultMessage;

/**
 *
 * @author Pedro Salgado
 */
@Local
public interface IUser {

    List<User> getAll();
    ResultMessage RegisterUser(String username, String password, String address);
    ResultMessage Login(String name, String password);
    boolean LogOff();
    User SeeProfile();
    ResultMessage UpdateProfile(String Address, String password, String CurretPass);
    double CheckBalance();
    ResultMessage LoadBalance(double increment);
    List<Message> CheckMessages();
    ResultMessage SendMessage(String Addressed, String Subject, String Message);
    String getUsername();
    boolean SuspendAccount();
    
}