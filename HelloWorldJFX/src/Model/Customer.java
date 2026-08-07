package Model;

import DBAccess.DBCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Customer class that defines customer parameters, gets and sets attributes. Also adds and deletes
 * customer objects from allCustomers list.
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String  customerPostalCode;
    private String customerPhoneNumber;
    private String customerCountry;
    private int customerDivision;
    private String customerDivisionName;

    public static ObservableList<Customer> allCustomers = DBCustomers.getAllCustomers();
    /** Customer constructor */
    public Customer(int customerId, String customerName, String customerAddress, String  customerPostalCode, String customerPhoneNumber, String customerCountry, int customerDivision, String customerDivisionName){
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerCountry = customerCountry;
        this.customerDivision = customerDivision;
        this.customerDivisionName = customerDivisionName;
    }
    /** getter for customerId */
    public int getCustomerId(){
        return customerId;
    }
    /** getter for customerName */
    public String getCustomerName(){
        return customerName;
    }
    /** getter for customerAddress */
    public String getCustomerAddress(){
        return customerAddress;
    }
    /** getter for customerPostalCode */
    public String getCustomerPostalCode(){
        return customerPostalCode;
    }
    /** getter for customerPhoneNumber */
    public String getCustomerPhoneNumber(){
        return customerPhoneNumber;
    }
    /** getter for customerCountry */
    public String getCustomerCountry(){
        return customerCountry;
    }
    /** getter for customerDivision */
    public int getCustomerDivision(){
        return customerDivision;
    }
    /** getter for customerDivisionName */
    public String getCustomerDivisionName() {
        return customerDivisionName;
    }
    /**getAllCustomers method to return a list of all customer from the database*/
    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }
    /** removeCustomer method to delete customer from list and database */
    public static boolean removeCustomer(Customer customer) throws SQLException {
        if(allCustomers.contains(customer)){
            allCustomers.remove(customer);
            return true;
        }
        else{
            return false;
        }
    }
    /** addCustomer method to add customer to list and database */
    public static void addCustomer(Customer customer) throws SQLException {
        allCustomers.add(customer);
        DBCustomers.insertCustomer(customer);
    }
    /**updateCustomer to updateCustomer from database */
    public static void updateCustomer(Customer customer) throws SQLException {

        DBCustomers.updateCustomer(customer);
    }
    /**getAllCustomerIds method that returns a list of all the customerIds */
    public static ObservableList getAllCustomerIds(){
        ObservableList allCustomerIds = FXCollections.observableArrayList();
        for(Customer customer : allCustomers){
            allCustomerIds.add(customer.getCustomerId());
        }
        return allCustomerIds;
    }
    /** overriding toString to contain the customerId and customerName */
    @Override
    public String toString(){
        return(customerId + " " + customerName);
    }
}
