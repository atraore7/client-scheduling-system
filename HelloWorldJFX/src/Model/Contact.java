package Model;

import DBAccess.DBContacts;
import javafx.collections.ObservableList;

/**
 * Contact class that defines a contact object, gets contact attributes. Also contains a list
 * of all contacts.
 */
public class Contact {
    private int contactId;
    private String contactName;

    public static ObservableList<Contact> allContacts = DBContacts.getAllContacts();
    /** Contact contructor */
    public Contact(int contactId, String contactName){
        this.contactId = contactId;
        this.contactName = contactName;
    }
    /** returns contactId */
    public int getContactId() {
        return contactId;
    }
    /** returns contactName */
    public String getContactName() {
        return contactName;
    }
    /** getAllContacts method that returns a list of contacts gotten from the database.*/
    public static ObservableList<Contact> getAllContacts() {

        return allContacts;
    }

    /**overriding the toString method to contain the contactId and contactName */
    @Override
    public String toString(){
        return(contactId + " " + contactName);
    }
}
