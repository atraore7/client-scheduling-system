package sample;

import Interfaces.MessageInterface;
import Interfaces.SumInterface;
import Model.Appointment;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Class called CustomerAppointmentsController that is a controller for the CustomerAppointments screen.
 *
 */
public class CustomerAppointmentsController implements Initializable {
    public ComboBox customerCombo;
    public Label label;
    private Scene mainScene;
    private Stage mainStage;
    private Parent mainRoot;

    /**
     * handleSubmitButton is a method for handling the click of the submit button on the CustomerAppointments screen.
     * When the user clicks the submit button the number of appointments the selected customer has
     * will appear below.
     * The first lambda expression is for the SumInterface this adds two values together. The lambda
     * expression allows me to count the selected customers appointments.
     * The second lambda expression in this method is for MessageInterface this will write out a custom message with the selected
     * customers' name and the number of appointment he/she has scheduled. This improves the function because it allow the
     * program to print out a custom message instead of just the number of appointments.
     * @param actionEvent
     */
    public void handleSubmitButton(ActionEvent actionEvent) {
        Customer customer = (Customer) customerCombo.getValue();
        String customerName = customer.getCustomerName();
        int customerId = customer.getCustomerId();
        int i = 0;
        //Lambda expression for ReportInterface (n1 + n2 is defining what the expression is going to do)
        SumInterface sum = (n1, n2) -> n1 + n2;
        for(Appointment appointment : Appointment.getAllAppointments()){
            if(customerId == appointment.getCustomerId()){
                //enter values into lambda expressions and assigning the returned value to i.
                //this will increment i by 1 each type an appointment is found.
                i = sum.calculateSum(i,1);
            }
        }
        //Lambda expression for MessageInterface
        MessageInterface message = (s1, s2) -> s1 + " has " + s2 + " appointment(s) scheduled.";
        //setting label text to the returned message from the lambda expression
        label.setText(message.messageMethod(customerName, i));
    }

    /**
     * handleBackButton is a method that handles the back button on click.
     * This method sends the user back to the main screen MainScreen.fxml
     * @param actionEvent
     * @throws IOException
     */
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        //changes scene to MainScreen
        mainRoot = FXMLLoader.load(getClass().getResource("../fxml/MainScreen.fxml"));
        mainStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        mainScene = new Scene(mainRoot);
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    /**
     * Initialization that sets the customerCombo box to all customers.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setting items in the customer combo box to all customers.
        customerCombo.setItems(Customer.getAllCustomers());
    }
}
