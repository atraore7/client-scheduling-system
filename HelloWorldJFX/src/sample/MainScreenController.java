package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Class called MainScreenController that is a controller for the MainScreen.
 */
public class MainScreenController {

    public Button manageAppointmentsButton;
    public Button manageCustomersButton;
    private Scene customerScene;
    private Stage customerStage;
    private Parent customerRoot;
    private Scene scheduleScene;
    private Stage scheduleStage;
    private Parent scheduleRoot;
    private Parent typeMonthRoot;
    private Stage typeMonthStage;
    private Scene typeMonthScene;
    private Parent contactRoot;
    private Stage contactStage;
    private Scene contactScene;
    private Parent custApptRoot;
    private Stage custApptStage;
    private Scene custApptScene;

    /**
     * Directs user to Appointments screen on click Manage Appointments.
     * @param actionEvent
     * @throws IOException
     */
    public void handleManageAppointments(ActionEvent actionEvent) throws IOException {
        scheduleRoot = FXMLLoader.load(getClass().getResource("../fxml/AppointmentsScreen.fxml"));
        scheduleStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scheduleScene = new Scene(scheduleRoot);
        scheduleStage.setScene(scheduleScene);
        scheduleStage.show();

    }

    /**
     * Directs user to Customer screen on click Manage Customers.
     * @param actionEvent
     * @throws IOException
     */
    public void handleManageCustomers(ActionEvent actionEvent) throws IOException {
        customerRoot = FXMLLoader.load(getClass().getResource("../fxml/CustomerScreen.fxml"));
        customerStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        customerScene = new Scene(customerRoot);
        customerStage.setScene(customerScene);
        customerStage.show();

    }

    /**
     * Direct users to by month by type screen on click By Type By Month button.
     * @param actionEvent
     * @throws IOException
     */
    public void handleByTypeAndMonthButton(ActionEvent actionEvent) throws IOException {
        typeMonthRoot = FXMLLoader.load(getClass().getResource("../fxml/ByMonthAndType.fxml"));
        typeMonthStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        typeMonthScene = new Scene(typeMonthRoot);
        typeMonthStage.setScene(typeMonthScene);
        typeMonthStage.show();
    }

    /**
     * Directs user to schedule by contact screen on click Schedule By Contact button.
     * @param actionEvent
     * @throws IOException
     */
    public void handleScheduleByContactButton(ActionEvent actionEvent) throws IOException {
        contactRoot = FXMLLoader.load(getClass().getResource("../fxml/ScheduleByContact.fxml"));
        contactStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        contactScene = new Scene(contactRoot);
        contactStage.setScene(contactScene);
        contactStage.show();
    }

    /**
     * Directs user to customer appointments screen on click Customers Appointments button
     * @param actionEvent
     * @throws IOException
     */
    public void handleCustomersAppointments(ActionEvent actionEvent) throws IOException {
        custApptRoot = FXMLLoader.load(getClass().getResource("../fxml/CustomerAppointments.fxml"));
        custApptStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        custApptScene = new Scene(custApptRoot);
        custApptStage.setScene(custApptScene);
        customerStage.show();
    }
}
