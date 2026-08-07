package DBAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Database access class called DBCustomers for getting, updating, and adding customers
 * within the database.
 */
public class DBCustomers {
    /**
     * getAllCustomers method to get customers out of the database and into a list
     */
    public static ObservableList<Customer> getAllCustomers(){
        //creating allCustomersList
        ObservableList<Customer> allCustomersList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Create_Date, Country, customers.Division_ID, first_level_divisions.Division\n" +
                    "FROM customers, countries, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID\n" +
                    "AND first_level_divisions.Country_ID = countries.Country_ID;";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while(resultSet.next()){
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phoneNumber = resultSet.getString("Phone");
                String country = resultSet.getString("Country");
                int division = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");



                //creating a customer object for each row that passes through the while loop
                Customer customer = new Customer(customerId, customerName, address, postalCode,phoneNumber,country,division,divisionName);
                //adding each customer to the list created at the beginning of the method
                allCustomersList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomersList;

    }

    /**
     * deleteCustomer method that deletes customers from database by customerId
     * @param customerId
     * @return
     * @throws SQLException
     */
    public static int deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM Customers WHERE Customer_Id = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * insertCustomer method that inserts customers into the database.
     * @param customer
     * @return
     * @throws SQLException
     */
    public static int insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers(Customer_Id, Customer_Name, \n" +
                "Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
        ps.setString(2, customer.getCustomerName());
        ps.setString(3, customer.getCustomerAddress());
        ps.setString(4, customer.getCustomerPostalCode());
        ps.setString(5, customer.getCustomerPhoneNumber());
        ps.setInt(6, customer.getCustomerDivision());

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    /**
     * updateCustomer method that updates customers by customerId
     * @param customer
     * @return
     * @throws SQLException
     */
    public static int updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, " +
                "Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?;\n";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getCustomerAddress());
        ps.setString(3, customer.getCustomerPostalCode());
        ps.setString(4, customer.getCustomerPhoneNumber());
        ps.setInt(5, customer.getCustomerDivision());
        ps.setInt(6, customer.getCustomerId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
