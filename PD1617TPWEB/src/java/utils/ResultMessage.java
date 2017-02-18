package utils;

public enum ResultMessage {
    AskAccountReactivationSucess("The request to reactivate the accout has been send"),
    AskAccountReactivationInsucess("The indicated account is not suspended"),
    AskAccountSuspencionSucess("The request to suspend account have been send to the administrator"),
    AskAccountSuspencionInsucess("It was impossible to send he request of account suspencion, please try again later"),
    LoginSucess("Logged with sucess"),
    LoginInvalidUsername("Invalid Username specified"),
    LoginInvalid("Invalid username or password"),
    LoginUserNotFound("User not found or invalid username/password"),
    LoginAllreadyLogged("User already logged"),
    RegisterAllreadyExist("Username already exist"),
    RegisterSucess("User registed successfully in the system"),
    RegisterInvalid("Invalid username or password specified"),
    UpdateProfileValid("Update Successfull"),
    UpdateProfileInvalid("Update invalid"),
    UpdateProfileWrongPass("Your current password is wrong"),
    LoadBalanceValid("Your load balance was successfully made"),
    LoadBalanceInvalid("Load balance invalid"),
    SendMessageSuccess("Your message was sent with success"),
    SendMessageUnsuccess("Something wrong happen to your message!"),
    SendMessageNoUser("The user you pretend send the message does not exist"),
    CreateItemSuccess("The Item was created with success"),
    CreateItemUnsuccess("The item creation have fail"),
    AuctionCreated("Auction created with success"),
    AuctionNotFound("The specified auction cannot be found"),
    AuctionNotCreated("An error have occur when trying to create a new auction"),
    AuctionAlreadyCreated("The auction for this item is already occurring"),
    AccountActivated("Account successfully activated"),
    AccountAllreadyActivated("Account as allready Activated"),
    AccountNotActivated("Account not activated yet"),
    AccountSuspended("Account suspended"),
    AccountNotSuspended("Account as not suspended"),
    AccountAllreadySuspended("Account as allready suspended"),
    AccountReActivated("Account re-activated"),
    AccountPasswordChanged("Password changed successfully"),
    AccountPasswordNotChanged("An error occur, password not changed"),
    AccountReactivation("The request for the account reactivation was sent to the Admin, please wait a while"),
    AccountReactivationFail("This accout is not suspend"),
    AccountReactivationNoUser("This account doesn't exist"),
    FollowItemSucess("Item added to the follow list with sucess!"),
    ErrorAdmin("The admin can´t do this operation!"),
    FollowItemAlreadyFollow("You already follow this Item"),
    ItemNotExist("The indicated item does not exist"),
    UserNotExist("The indicated user does not exist"),
    CancelFollowItemSucess("The item was successfuly removed from the follow list"),
    CancelFollowItemAlreadyNotFollow("You don't follow the indicated Item"),
    CancelFollowItemError("Some error have ocurred during the procedure, please try again later"),
    CategoriesNotFound("Unable to get categories info"),
    CategoryAdded("New category added with sucess"),
    CategoryUpdated("Category updated with sucess"),
    CategoryErrorAdd("Error when trying to add new category"),
    CategoryAllreadyExists("An category with the name choosen allready exists"),
    CategoryErrorUpdate("Error when trying to updated category"),
    ReportSuccess("The report was sended to the administrator"),
    ReportInsuccess("The report can't be made, please make sure that you have insert the right information"),
    AuctionNotExist("The auction that you try access don't exist anymore"),
    AuctionAlreadyFinish("The Auction have already finished"),
    BidInsuccess("You could't do the bid, please verify your balance"),
    BidInsuccess2("You could't do a bid with a value lower than the current price"),
    BidSuccess("You bid the item"),
    BuyNowSuccess("You buy the item"),
    BuyNowInsuccess("You didn't buy the item"),
    BuyNowInsuccess2("You cant'n buy an item by the buy now price when the current price is higher"),
    CancelItemSucess("You have removed the item"),
    CancelItemInsucess("You can't remove the item, please contact the administrator."),
    ItemOwner("You are the item owner");

    private String Message;

    ResultMessage(String Message) {
        this.Message = Message;
    }

    public String Message() {
        return this.Message;
    }
}
