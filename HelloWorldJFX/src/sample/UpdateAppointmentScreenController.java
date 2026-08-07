package sample;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/**
 * Class called UpdateAppointmentScreenController that is a controller for the UpdateAppointmentScreen.
 */
public class UpdateAppointmentScreenController implements Initializable {
    public TextField apptIdInput;
    public TextField apptTitleInput;
    public TextField apptDescriptionInput;
    public TextField apptLocationInput;
    public ComboBox apptTypeCombo;
    public Button saveButton;
    public Button cancelButton;
    public DatePicker appointmentDatepicker;
    public ComboBox<String> startTimeCombo;
    public ComboBox<String> endTimeCombo;
    public ComboBox<Customer> apptCustomerCombo;
    public ComboBox<User> apptUserCombo;
    public ComboBox<Contact> apptContactCombo;
    private Scene scheduleScene;
    private Stage scheduleStage;
    private Parent scheduleRoot;
    private Scene appointmentScene;
    private Stage appointmentStage;
    private Parent appointmentroot;
    //private Appointment selectedAppointment;
    private Appointment selectedAppointment;

    /**
     * Sets each field will selected appointment information.
     * @param appointment
     */
    public void init(Appointment appointment){
        selectedAppointment = appointment;
        apptIdInput.setText(String.valueOf(selectedAppointment.getApptId()));
        apptTitleInput.setText(selectedAppointment.getTitle());
        apptDescriptionInput.setText(selectedAppointment.getDescription());
        apptLocationInput.setText(selectedAppointment.getLocation());
        for(Contact contact : Contact.getAllContacts()){
            if(selectedAppointment.getContactId() == contact.getContactId()){
                apptContactCombo.setValue(contact);
            }
        }
        for(Customer customer : Customer.getAllCustomers()){
            if(selectedAppointment.getCustomerId() == customer.getCustomerId()){
                apptCustomerCombo.setValue(customer);
            }
        }
        for(User user : User.getAllUsers()){
            if(selectedAppointment.getUserId() == user.getUser_Id()){
                apptUserCombo.setValue(user);
            }
        }
        apptTypeCombo.setValue(selectedAppointment.getType());
        //converting the appointment LocalDateTime startDateTime to a LocalDate startdate in order to
        //populate the appointmentDatePicker value
        LocalDate startDate = selectedAppointment.getStartDateTime().toLocalDate();
        appointmentDatepicker.setValue(startDate);
        //same as above for the startTimeCombo and endTimeCombo
        LocalTime startTime = selectedAppointment.getStartDateTime().toLocalTime();
        startTimeCombo.setValue(String.valueOf(startTime));
        LocalTime endTime = selectedAppointment.getEndDateTime().toLocalTime();
        endTimeCombo.setValue(String.valueOf(endTime));
    }

    /**
     * Sets combo items to corresponding lists.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptContactCombo.setItems(Contact.getAllContacts());
        apptUserCombo.setItems(User.getAllUsers());
        apptCustomerCombo.setItems(Customer.getAllCustomers());
        startTimeCombo.setItems(Appointment.getAppointmentTimes());
        endTimeCombo.setItems(Appointment.getAppointmentTimes());
        apptTypeCombo.setItems(Appointment.getAppointmentTypes());

    }

    /**
     * Updates selected appointment with new information on click save.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void handleSaveButton(ActionEvent actionEvent) throws IOException, SQLException {
        int apptId = selectedAppointment.getApptId();
        String apptTitle = apptTitleInput.getText();
        String apptDescription = apptDescriptionInput.getText();
        String apptLocation = apptLocationInput.getText();
        Contact apptContact = (Contact) apptContactCombo.getValue();
        int apptContactId = apptContact.getContactId();
        String apptType = apptTypeCombo.getValue().toString();
        LocalDate startdate = appointmentDatepicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(startTimeCombo.getValue(), formatter);
        LocalTime endTime = LocalTime.parse(endTimeCombo.getValue(), formatter);
        LocalDateTime startDateTime = startdate.atTime(startTime);
        LocalDateTime endDateTime = startdate.atTime(endTime);
        Customer customer = (Customer) apptCustomerCombo.getValue();
        int customerId = customer.getCustomerId();
        User user = (User) apptUserCombo.getValue();
        int userId = user.getUser_Id();

        //Appointment.deleteAppointment(selectedAppointment);
        Appointment appointment = new Appointment(apptId, apptTitle, apptDescription, apptLocation, apptContactId, apptType, startDateTime, endDateTime, customerId, userId);
        //Appointment.addAppointment(appointment);
        Appointment.updateAppointment(appointment);

        scheduleRoot = FXMLLoader.load(getClass().getResource("../fxml/AppointmentsScreen.fxml"));
        scheduleStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scheduleScene = new Scene(scheduleRoot);
        scheduleStage.setScene(scheduleScene);
        scheduleStage.show();

    }

    /**
     * Directs user to appointments screen on cancel button click.
     * @param actionEvent
     * @throws IOException
     */
    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        scheduleRoot = FXMLLoader.load(getClass().getResource("../fxml/AppointmentsScreen.fxml"));
        scheduleStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scheduleScene = new Scene(scheduleRoot);
        scheduleStage.setScene(scheduleScene);
        scheduleStage.show();
    }



}
