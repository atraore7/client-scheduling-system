package sample;

import DBAccess.DBCountry;
import DBAccess.DBDivisions;
import Model.Country;
import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
/**
 * Class called AddCustomerScreenController that is a controller for the AddCustomerScreen.
 */
public class AddCustomerScreenController implements Initializable {
    public Button cancelButton;
    public TextField customerIdInput;
    public TextField customerNameInput;
    public TextField customerAddressInput;
    public TextField customerPostalInput;
    public TextField customerNumberInput;
    public ComboBox<Model.Division> divisionCombo;
    public ComboBox<Model.Country> countryCombo;
    private Scene customerScene;
    private Stage customerStage;
    private Parent customerRoot;

    /**
     * handleCancel is an action event handler that directs the user to the Customer screen.
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

    /**
     * onSave is an action event handler that takes the user input and saves it to a customer object.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            String customerName = customerNameInput.getText();
            String customerAddress = customerAddressInput.getText();
            String customerPostal = customerPostalInput.getText();
            String customerPhone = customerNumberInput.getText();
            Country customerCountry = countryCombo.getValue();
            String customersCountry = customerCountry.getCountryId();
            Division customerDivision = divisionCombo.getValue();
            int customersDivision = customerDivision.getDivisionId();
            String customerDivisionName = customerDivision.getDivisionName();

            Customer customer = new Customer(autoGenerateCustomerId(), customerName, customerAddress, customerPostal, customerPhone, customersCountry, customersDivision, customerDivisionName);
            Customer.addCustomer(customer);

            customerRoot = FXMLLoader.load(getClass().getResource("../fxml/CustomerScreen.fxml"));
            customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            customerScene = new Scene(customerRoot);
            customerStage.setScene(customerScene);
            customerStage.show();
        }
        catch(NullPointerException e){
            Alert emptyFieldAlert = new Alert(Alert.AlertType.ERROR);
            emptyFieldAlert.setContentText("Please complete all fields");
            emptyFieldAlert.showAndWait();
        }



    }

    /**
     * autoGenerateCusotmer Id is a method that generates a randomId
     * @return
     */
    public  int autoGenerateCustomerId(){
        //auto generates a random id
        int randomId = 0;
        Random rand = new Random();
        for (int i=0;i < 10;i++)
        {
            randomId = rand.nextInt(1000);
        }
        return randomId;

    }
    //initialize AddCustomer screen
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //setting options for the Country combo box
        ObservableList<Country> country = DBCountry.getAllCountries();
        countryCombo.setItems(country);



    }

    /**
     * onSelectCountry is a method that sets the division combo box depending on the selected country
     * @param actionEvent
     */
    //when user selects a country the division combo box will display the corresponding divisions.
    public void onSelectCountry(ActionEvent actionEvent) {
        Country selectedCountry = countryCombo.getValue();
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
}
