package DBAccess;

import Database.JDBC;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Database access class for getting contacts out of the database.
 */
public class DBContacts {
    /**
     * getAllContacts method that gets contacts out of the database and into a list
     */
    public static ObservableList<Contact> getAllContacts(){
        //creating allContactsList
        ObservableList<Contact> allContactsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Contact_ID, Contact_Name FROM contacts;";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");

                //creating a contact object for each row that passes through the while loop
                Contact contact = new Contact(contactId, contactName);
                //adding each contact to the list created at the beginning of the method
                allContactsList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContactsList;

    }
}
