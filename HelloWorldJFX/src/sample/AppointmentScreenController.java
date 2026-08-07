package sample;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import DBAccess.DBVirtualAppointments;
import Model.Appointment;
import Model.Customer;
import Model.VirtualAppointment;
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
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Class called AppointmentScreenController that is a controller for the AppointmentScreen.
 */
public class AppointmentScreenController implements Initializable {
    public Button addApptButton;
    public Button updateApptButton;
    public Button deleteApptButton;
    public Button backButton;
    public TextField searchApptInput;
    public TableView apptTable;
    public TableColumn apptIDCol;
    public TableColumn apptTitleCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptLocationCol;
    public TableColumn apptContactCol;
    public TableColumn apptTypeCol;
    public TableColumn apptStartCol;
    public TableColumn apptEndCol;
    public TableColumn apptCustomerIdCol;
    public TableColumn apptUserIdCol;
    public RadioButton allRadioButton;
    public RadioButton monthRadioButton;
    public RadioButton weekRadioButton;
    public TextField searchApptInput1;
    public RadioButton monthRadioButton1;
    public RadioButton weekRadioButton1;
    public RadioButton allRadioButton1;
    public TableView virtualApptTable;
    public TableColumn apptIDCol1;
    public TableColumn apptTitleCol1;
    public TableColumn apptDescriptionCol1;
    public TableColumn apptLocationCol1;
    public TableColumn apptContactCol1;
    public TableColumn apptTypeCol1;
    public TableColumn apptStartCol1;
    public TableColumn apptEndCol1;
    public TableColumn apptCustomerIdCol1;
    public TableColumn apptUserIdCol1;
    public Button updateApptButtonVirtual;
    public Button deleteApptButtonVirtual;
    private Scene mainScene;
    private Stage mainStage;
    private Parent mainRoot;
    private Scene addApptScene;
    private Stage addApptStage;
    private Parent addApptRoot;
    private Scene updateApptScene;
    private Stage updateApptStage;
    private Parent updateApptRoot;

    /**
     * handleAddAppt method handles on add appointment button click. This directs the user to
     * the add appointment screen.
     * @param actionEvent
     * @throws IOException
     */
    public void handleAddAppt(ActionEvent actionEvent) throws IOException {
        addApptRoot = FXMLLoader.load(getClass().getResource("../fxml/AddAppointmentScreen.fxml"));
        addApptStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        addApptScene = new Scene(addApptRoot);
        addApptStage.setScene(addApptScene);
        addApptStage.show();
    }

    /**
     * handleUpdateAppt method handles on update appointment button click. This directs the user to
     * the update appointment screen.
     * @param actionEvent
     * @throws IOException
     */
    public void handleUpdateAppt(ActionEvent actionEvent) throws IOException {
        //change screen to UpdateAppointmentScreen.fxml
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/UpdateAppointmentScreen.fxml"));
        Parent updateAppointmentRoot = loader.load();
        Scene updateAppointmentWindow = new Scene(updateAppointmentRoot);

        //creates access to UpdateAppointmentController and passes it into variable controller
        UpdateAppointmentScreenController controller = loader.getController();
        controller.init((Appointment) apptTable.getSelectionModel().getSelectedItem());

        Stage updateAppointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        updateAppointmentStage.setScene(updateAppointmentWindow);
        updateAppointmentStage.show();

    }

    /**
     * handleDeleteAppt method will delete the selected appointment in the table from the datbase
     * @param actionEvent
     * @throws SQLException
     */
    public void handleDeleteAppt(ActionEvent actionEvent) throws SQLException {
        try{
            Appointment selectedAppt = (Appointment) apptTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                DBAppointments.deleteAppointment(selectedAppt.getApptId());
                //DBAppointments.deleteAppointment(selectedAppt);
                Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
                newAlert.setContentText("Appointment ID: " + selectedAppt.getApptId() +
                        " Appointment Type: " + selectedAppt.getType() +
                        " was successfully cancelled.");
                newAlert.showAndWait();
                apptTable.setItems(DBAppointments.getAllAppointments());
            }

        }
        catch(NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No appointment is selected");
            alert.showAndWait();
        }


    }

    /**
     * handleBackButton will direct the user to the main screen.
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
     * handleSearchAppt will display the corresponding appointment in the apptTable.
     * Users can search by id or name.
     * @param actionEvent
     */
    public void handleSearchAppt(ActionEvent actionEvent) {
        ObservableList<Appointment> matchedAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = Appointment.getAllAppointments();

        //loops through the allAppointments list to set the matched items to the matchedAppointmet list.
        for(Appointment appointment : allAppointments){
            //checks if input matches aptTitle
            if(appointment.getTitle().contains(searchApptInput.getText())){
                matchedAppointments.add(appointment);
            }
            //checks if input matches aptId
            if(appointment.getApptId() == Integer.parseInt(searchApptInput.getText())){
                matchedAppointments.add(appointment);
            }
        }
        //sets table items to the appointments in the matchedAppointments list
        apptTable.setItems(matchedAppointments);
        String search = searchApptInput.getText();
        //if there is nothing in the search box sets the items to allAppointments
        if(search.length() == 0){
            apptTable.setItems(allAppointments);
        }
    }

    /**
     * initializes the apptTable will all of the appointments from the database.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptTable.setItems(Appointment.getAllAppointments());

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        virtualApptTable.setItems(DBVirtualAppointments.getVirtualAppointments());

        apptIDCol1.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        apptTitleCol1.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol1.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol1.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptStartCol1.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        apptEndCol1.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        apptCustomerIdCol1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol1.setCellValueFactory(new PropertyValueFactory<>("userId"));
        apptTypeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    /**
     * handleAllRadio handles the all radio button to display all appointments in the table.
     * @param actionEvent
     */
    public void handleAllRadio(ActionEvent actionEvent) {
        apptTable.setItems(Appointment.getAllAppointments());
    }

    /**
     * handleMonthRadio handles the month radio button to display all appointments for the current
     * month in the table.
     * @param actionEvent
     */
    public void handleMonthRadio(ActionEvent actionEvent) {
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
        Month currentMonth = LocalDateTime.now().getMonth();
        int currentYear = LocalDateTime.now().getYear();
        for(Appointment appointment : Appointment.getAllAppointments()){
            Month appointmentMonth = appointment.getStartDateTime().getMonth();
            int appointmentYear = appointment.getStartDateTime().getYear();
            if(appointmentMonth.equals(currentMonth) && appointmentYear == currentYear){
                monthAppointments.add(appointment);
            }
            apptTable.setItems(monthAppointments);
        }
    }

    /**
     * handleWeekRadio handle the week radio button that diplays the appointments in the current
     * week in the table.
     * @param actionEvent
     */
    public void handleWeekRadio(ActionEvent actionEvent) {
        ObservableList weekAppts = FXCollections.observableArrayList();
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        LocalDate currentWeekPlus7Days = currentDate.plusDays(7);
        for(Appointment appointment : Appointment.getAllAppointments()){
            LocalDate apptDate = appointment.getStartDateTime().toLocalDate();
            if(apptDate.isBefore(currentWeekPlus7Days) && apptDate.isAfter(currentDate) || apptDate.equals(currentDate)) {
                weekAppts.add(appointment);

            }
        }
        apptTable.setItems(weekAppts);
    }

    public void handleVirtualUpdate(ActionEvent actionEvent) {
    }

    public void handleVirtualDelete(ActionEvent actionEvent) {
        try{
            VirtualAppointment selectedAppt = (VirtualAppointment) virtualApptTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                DBVirtualAppointments.deleteVirtualAppointment(selectedAppt.getApptId());
                Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
                newAlert.setContentText("Appointment ID: " + selectedAppt.getApptId() +
                        " Appointment Type: " + selectedAppt.getType() +
                        " was successfully cancelled.");
                newAlert.showAndWait();
                virtualApptTable.setItems(DBVirtualAppointments.getVirtualAppointments());
            }

        }
        catch(NullPointerException | SQLException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No appointment is selected");
            alert.showAndWait();
        }

    }
}
