package sample;


import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Class called CustomerScreenController that is a controller for the CustomerScreen.
 */
public class CustomerScreenController implements Initializable{
    public TableColumn customerIDCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn customerPostalCol;
    public TableColumn customerDivisionCol;
    public TableColumn customerPhoneCol;
    public TextField searchInput;
    public Button addCustomerButton;
    public Button deleteCustomerButton;
    public Button updateCustomerButton;
    public Button backButton;
    public TableView<Customer> customerTable;
    public TableColumn customerCreateDateCol;
    private Scene addCustomerScene;
    private Stage addCustomerStage;
    private Parent addCustomerRoot;
    private Scene mainScene;
    private Stage mainStage;
    private Parent mainRoot;
    private Scene updateCustomerScene;
    private Stage updateCustomerStage;
    private Parent updateCustomerRoot;

    /**
     * initializes the customer table will all customers.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        customerTable.setItems(Customer.getAllCustomers());

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));

    }

    /**
     * Displays results corresponding to users search input. Users can search by id or name.
     * @param actionEvent
     */
    public void handleSearch(ActionEvent actionEvent) {
        ObservableList<Customer> matchedCustomers = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = Customer.getAllCustomers();

        //loops through the allCustomer list to set the matched items to the matchedCustomers list.
        for(Customer c : allCustomers){
            if(c.getCustomerName().contains(searchInput.getText())){
                matchedCustomers.add(c);
            }
            if(Integer.toString(c.getCustomerId()).contains(searchInput.getText())){
                matchedCustomers.add(c);
            }
        }
        //if there is any customer object in the matchedCustomers list this will set the customerTable to the matchedCustomers list.
        if(matchedCustomers.size() > 0){
            customerTable.setItems(matchedCustomers);
        }

    }

    /**
     * Directs user to add customer screen when add customer button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void handleAddCustomer(ActionEvent actionEvent) throws IOException {
        addCustomerRoot = FXMLLoader.load(getClass().getResource("../fxml/AddCustomerScreen.fxml"));
        addCustomerStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        addCustomerScene = new Scene(addCustomerRoot);
        addCustomerStage.setScene(addCustomerScene);
        addCustomerStage.show();
    }

    /**
     * Deletes selected customer from the database on click delete.
     * @param actionEvent
     * @throws SQLException
     */
    public void handleDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        try{
            Customer customer = (Customer) customerTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete " + customer.getCustomerName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){

                int customerid = customer.getCustomerId();

                for(Appointment appointment1 : Appointment.getAllAppointments()){
                    System.out.println(appointment1.getTitle() + " " + appointment1.getCustomerId());
                }

                for(Appointment appointment : Appointment.getAllAppointments()){
                    System.out.println(appointment.getTitle() + " " + appointment.getCustomerId());
                    System.out.println(Appointment.getAllAppointments().size());
                    if(appointment.getCustomerId() == customerid){
                        Appointment.deleteAppointment(appointment);

                        DBAppointments.deleteAppointment(appointment.getApptId());
                        /*Appointment.allAppointments.remove(appointment);

                         */
                    }


                }


                DBCustomers.deleteCustomer(customerid);
                Customer.removeCustomer(customer);
            }
        }
        catch (NullPointerException nullPointerException){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No customer is selected to delete.");
            alert.showAndWait();
        }

    }

    /**
     * Direct user to update customer screen on click update customer.
     * @param actionEvent
     * @throws IOException
     */
    public void handleUpdateCustomer(ActionEvent actionEvent) throws IOException {
        try{
            //change screen to UpdateCustomerScreen.fxml
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/UpdateCustomer.fxml"));
            Parent updateCustomerRoot = loader.load();
            Scene updateCustomerWindow = new Scene(updateCustomerRoot);

            //creates access to UpdateCustomerController and passes it into variable controller
            UpdateCustomerController controller = loader.getController();
            controller.init(customerTable.getSelectionModel().getSelectedItem());


            Stage updateCustomerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            updateCustomerStage.setScene(updateCustomerWindow);
            updateCustomerStage.show();
        }
        catch (NullPointerException nullPointerException){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a customer.");
            alert.showAndWait();

        }


    }

    /**
     * Direct user to main screen on click back.
     * @param actionEvent
     * @throws IOException
     */
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        mainRoot = FXMLLoader.load(getClass().getResource("../fxml/MainScreen.fxml"));
        mainStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        mainScene = new Scene(mainRoot);
        mainStage.setScene(mainScene);
        mainStage.show();
    }
}
