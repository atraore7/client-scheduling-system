package sample;

import DBAccess.DBVirtualAppointments;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Class called AddAppointmentScreenController that is a controller for the AddAppointmentScreen.
 */
public class AddAppointmentScreenController implements Initializable {
    public Button saveButton;
    public Button cancelButton;
    public TextField apptIdInput;
    public TextField apptTitleInput;
    public TextField apptDescriptionInput;
    public TextField apptLocationInput;
    public ComboBox apptTypeCombo;
    public ComboBox apptContactCombo;
    public ComboBox startTimeCombo;
    public ComboBox endTimeCombo;
    public DatePicker appointmentDatepicker;
    public ComboBox customerIDCombo;
    public ComboBox userIDCombo;
    public RadioButton virtual;
    public RadioButton inOffice;
    private Scene scheduleScene;
    private Stage scheduleStage;
    private Parent scheduleRoot;

    /** initializing the screen so the combo boxes are set with the corresponding items */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //creating a list of user ids
        ObservableList userIds = FXCollections.observableArrayList();
        for(User user : User.getAllUsers()){
            userIds.add(user.getUser_Id());
        }
        //creating a list of customerIds
        ObservableList customerIds = FXCollections.observableArrayList();
        for(Customer customer : Customer.getAllCustomers()){
            customerIds.add(customer.getCustomerId());
        }

        //Populating Combo boxes.
        apptContactCombo.setItems(Contact.getAllContacts());
        startTimeCombo.setItems(Appointment.getAppointmentTimes());
        endTimeCombo.setItems(Appointment.getAppointmentTimes());
        userIDCombo.setItems(userIds);
        customerIDCombo.setItems(customerIds);
        apptTypeCombo.setItems(Appointment.getAppointmentTypes());


    }

    /**
     * handleSaveButton is an eventhandler for the save button.
     * This method will take the input from the user and save the appointment.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void handleSaveButton(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            String title = apptTitleInput.getText();
            String description = apptDescriptionInput.getText();
            String location = apptLocationInput.getText();
            Contact contact = (Contact) apptContactCombo.getValue();
            int contactId = contact.getContactId();
            int apptCustomerId = (int) customerIDCombo.getValue();
            int apptUserID = (int) userIDCombo.getValue();
            String type = apptTypeCombo.getValue().toString();
            //getting the value of appointmentDatePicker and assigning it to LocalDate appointmentDate
            LocalDate appointmentDate = appointmentDatepicker.getValue();
            //getting the string of the StartTimeCombo
            String startTime = (String) startTimeCombo.getValue();
            //parseing the StartTime string to a LocalTime
            LocalTime starttime = LocalTime.parse(startTime);
            //combining LocalTime and LocalDate into a LocalDateTime that is startDateTime
            LocalDateTime startDateTime = starttime.atDate(appointmentDate);
            //combining the endtime LocalTime and LocalDate to get a LocalDateTime endDateTime
            String endTime = (String) endTimeCombo.getValue();
            LocalTime endtime = LocalTime.parse(endTime);
            LocalDateTime endDateTime = endtime.atDate(appointmentDate);

            int startTimeHour = starttime.getHour();
            int endTimeHour = endtime.getHour();


            LocalDateTime localOpenTime = LocalDateTime.of(2020, Month.APRIL, 30, 8, 00, 00);
            ZoneId estZoneId = ZoneId.of("America/New_York");
            ZonedDateTime zonedOpenDateTime = localOpenTime.atZone(estZoneId);
            LocalDateTime localCloseDate = LocalDateTime.of(2020, Month.APRIL, 30, 22, 00, 00);
            ZonedDateTime zonedCloseDateTime = localCloseDate.atZone(estZoneId);
            int openTimeHour = zonedOpenDateTime.getHour();
            int closeTimeHour = zonedCloseDateTime.getHour();
            boolean overlap = false;
            boolean outsideBusinessHours = false;

            if (startTimeHour <= openTimeHour || endTimeHour >= closeTimeHour) {
                outsideBusinessHours = true;
                Alert timeAlert = new Alert(Alert.AlertType.ERROR);
                timeAlert.setContentText("Please select appointment times between 8:00 and 22:00 in EST");
                timeAlert.showAndWait();
            }
            else {
                for (Appointment appointment : Appointment.getAllAppointments()) {
                    int apptstarthour = appointment.getStartDateTime().getHour();
                    int apptendhour = appointment.getEndDateTime().getHour();


                    if (appointment.getCustomerId() == apptCustomerId) {
                            //if (appointment.getStartDateTime().equals(startDateTime) || appointment.getEndDateTime().equals(endDateTime)) {
                             if(startDateTime.toLocalDate().equals(appointment.getStartDateTime().toLocalDate()) && startDateTime.getHour() >= apptstarthour && endDateTime.getHour() <= apptendhour){
                                Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                                overlapAlert.setContentText("ERROR! Cannot Schedule appointment due to overlapping appointment times for customer");
                                overlapAlert.showAndWait();
                                overlap = true;
                                break;

                            }
                    }

                }
            }
            if(overlap == false && outsideBusinessHours == false) {
                if(virtual.isSelected()) {
                    String virtualString = "Virtual";
                    VirtualAppointment newappointment = new VirtualAppointment(autoGenerateAppointmentId(), title, description, location, contactId, type, startDateTime, endDateTime, apptCustomerId, apptUserID, virtualString);
                    DBVirtualAppointments.insertVirtualAppointment(newappointment);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Appointment was saved");

                    scheduleRoot = FXMLLoader.load(getClass().getResource("../fxml/AppointmentsScreen.fxml"));
                    scheduleStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scheduleScene = new Scene(scheduleRoot);
                    scheduleStage.setScene(scheduleScene);
                    scheduleStage.show();
                }
                else{
                    Appointment newappointment = new Appointment(autoGenerateAppointmentId(), title, description, location, contactId, type, startDateTime, endDateTime, apptCustomerId, apptUserID);
                    Appointment.addAppointment(newappointment);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Appointment was saved");

                    scheduleRoot = FXMLLoader.load(getClass().getResource("../fxml/AppointmentsScreen.fxml"));
                    scheduleStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scheduleScene = new Scene(scheduleRoot);
                    scheduleStage.setScene(scheduleScene);
                    scheduleStage.show();
                }
            }
        }
        catch (NullPointerException e){
            Alert emptyFieldAlert = new Alert(Alert.AlertType.ERROR);
            emptyFieldAlert.setContentText("Please complete fields");
            emptyFieldAlert.showAndWait();
        }


    }
    /** autoGenerateAppointmetnId() will generate a randomId */
    public  int autoGenerateAppointmentId(){
        //auto generates a random id
        int randomId = 0;
        Random rand = new Random();
        for (int i=1000;i < 5000;i++)
        {
            randomId = rand.nextInt(1000);
        }
        return randomId;

    }

    /**
     * handleCancelButton is an action even handler that will direct the user to the appointments screen
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
