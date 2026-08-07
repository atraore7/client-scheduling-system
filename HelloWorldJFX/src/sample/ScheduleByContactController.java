package sample;

import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Class called ScheduleByContactController that is a controller for the ScheduleByContact screen.
 */
public class ScheduleByContactController implements Initializable {
    public ComboBox contactCombo;
    public TableColumn apptIdCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIdCol;
    public TableColumn userIdCol;
    public TableView apptTable;
    private Scene mainScene;
    private Stage mainStage;
    private Parent mainRoot;

    /**
     * Displays selected contacts' appointments on click submit.
     * @param actionEvent
     */
    public void handleSubmit(ActionEvent actionEvent) {
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));


        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        Contact contact = (Contact) contactCombo.getValue();

        for(Appointment appointment : Appointment.getAllAppointments()){
            if(appointment.getContactId() == contact.getContactId()){
                contactAppointments.add(appointment);
            }
        }
        apptTable.setItems(contactAppointments);
    }

    /**
     * Direct user to Main screen on click back button.
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

    /**
     * Initializes contact combo box items to all contacts.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCombo.setItems(Contact.getAllContacts());
    }
}
