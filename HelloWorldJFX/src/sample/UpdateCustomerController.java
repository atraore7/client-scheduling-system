package sample;

import DBAccess.DBCountry;
import DBAccess.DBDivisions;
import Model.Country;
import Model.Customer;
import Model.Division;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;
/**
 * Class called UpdateCustomerController that is a controller for the UpdateCustomer screen.
 */
public class UpdateCustomerController implements Initializable{
    public Button cancelButton;
    public TextField customerIdInput;
    public TextField customerNameInput;
    public TextField customerAddressInput;
    public TextField customerPostalInput;
    public TextField customerNumberInput;
    public ComboBox<Division> divisionCombo;
    public ComboBox<Country> countryCombo;
    public Button saveButton;
    private Scene customerScene;
    private Stage customerStage;
    private Parent customerRoot;
    private Customer selectedCustomer;

    /**
     * Sets input fields to the corresponding information of the selected customer.
     * @param customer
     */
    public void init(Customer customer) {
        selectedCustomer = customer;

        for (Country country : Country.getAllCountries()) {
            if (selectedCustomer.getCustomerCountry().equals("U.S")) {
                countryCombo.setValue(country);
                divisionCombo.setItems(DBDivisions.getUSDivisions());

            }
            if (selectedCustomer.getCustomerCountry().equals("UK")) {
                countryCombo.setValue(country);
            }
            if (selectedCustomer.getCustomerCountry().equals("Canada")) {
                countryCombo.setValue(country);
            }
        }

        for (Division division : DBDivisions.getAllDivisions()) {
            if (division.getDivisionId() == selectedCustomer.getCustomerDivision()) {
                divisionCombo.setValue(division);
            }
        }

        customerIdInput.setText(String.valueOf(selectedCustomer.getCustomerId()));
        customerNameInput.setText(selectedCustomer.getCustomerName());
        customerAddressInput.setText(selectedCustomer.getCustomerAddress());
        customerPostalInput.setText(selectedCustomer.getCustomerPostalCode());
        customerNumberInput.setText(selectedCustomer.getCustomerPhoneNumber());
        //divisionCombo.setValue(selectedCustomer.getCustomerDivisionName());
    }


    /**
     * Sets combo box items to their corresponding lists.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> countries = DBCountry.getAllCountries();
        countryCombo.setItems(countries);

    }


    /**
     * Updates selected customer with new information on click save.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void handleSaveButton(ActionEvent actionEvent) throws IOException, SQLException {
        int customerId = Integer.parseInt(customerIdInput.getText());
        String customerName = customerNameInput.getText();
        String customerAddress = customerAddressInput.getText();
        String customerPostal = customerPostalInput.getText();
        String customerPhone = customerNumberInput.getText();
        Country customerCountry = (Country) countryCombo.getValue();
        String customersCountry = customerCountry.getCountryId();
        Division customerDivision = (Division) divisionCombo.getValue();
        int customersDivision = customerDivision.getDivisionId();
        String customerDivisionName = customerDivision.getDivisionName();

        Customer.allCustomers.remove(selectedCustomer);
        Customer customer = new Customer(customerId, customerName, customerAddress, customerPostal, customerPhone, customersCountry, customersDivision, customerDivisionName);
        Customer.updateCustomer(customer);
        Customer.allCustomers.add(customer);


        customerRoot = FXMLLoader.load(getClass().getResource("../fxml/CustomerScreen.fxml"));
        customerStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        customerScene = new Scene(customerRoot);
        customerStage.setScene(customerScene);
        customerStage.show();
    }

    /**
     * sets division combo box to correspond divisions based on country selection.
     * @param actionEvent
     */
    public void handleCustomerCountry(ActionEvent actionEvent) {
        Country selectedCountry = (Country) countryCombo.getValue();
        if(selectedCountry.getCountryId().equals("1")){
            divisionCombo.setItems(DBDivisions.getUSDivisions());

        }
        if(selectedCountry.getCountryId().equals("2")){
            divisionCombo.setItems(DBDivisions.getUKDivisions());
        }
        if(selectedCountry.getCountryId().equals("3")){
            divisionCombo.setItems(DBDivisions.getCanadianDivisions());
        }

    }

    /**
     * Directs user to customer screen on click cancel.
     * @param actionEvent
     * @throws IOException
     */
    public void handleCancel(ActionEvent actionEvent) throws IOException {
        customerRoot = FXMLLoader.load(getClass().getResource("../fxml/CustomerScreen.fxml"));
        customerStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        customerScene = new Scene(customerRoot);
        customerStage.setScene(customerScene);
        customerStage.show();
    }
}
