package Model;

import DBAccess.DBCustomers;
import DBAccess.DBUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * User class that defines a user object and gets their attributes.
 */
public class User {
    private int user_Id;
    private String userName;
    private String userPassword;
    private Locale userLocale;
    private ZoneId userTimeZone;

    public static ObservableList<User> allUsers = DBUser.getAllUsers();
    /** override toString to contain userId and userName */
    @Override
    public String toString() {
        return user_Id + " " + userName;
    }
    /** User constructor */
    public User(int user_Id, String userName, String userPassword, Locale userLocale, ZoneId userTimeZone){
        this.user_Id = user_Id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userLocale = userLocale;
        this.userTimeZone = userTimeZone;

    }
    /** getter for userId */
    public int getUser_Id() {
        return user_Id;
    }
    /** getter for userName */
    public String getUserName() {
        return userName;
    }
    /** getter for userPassword */
    public String getUserPassword() {
        return userPassword;
    }
    /** getter for userLocale */
    public Locale getUserLocale() {
        return userLocale;
    }
    /** getter for userTimeZone */
    public ZoneId getUserTimeZone() {
        return userTimeZone;
    }
    /** getAllUsers method that returns a list of users from the database */
    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }
    /** getAllUserIds method that returns a list of all the user ids */
    public static ObservableList getAllUserIds(){
        ObservableList allUserIds = FXCollections.observableArrayList();
        for(User user : allUsers){
            allUserIds.add(user.getUser_Id());
        }
        return allUserIds;
    }
}


