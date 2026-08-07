package sample;

import Model.Appointment;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Class called ByMonthAndTypeController that is a controller for the ByMonthAndType Screen.
 */
public class ByMonthAndTypeController implements Initializable {
    public Label label;
    public ComboBox monthCombo;
    public ComboBox typeCombo;
    public Button backButton;
    private Scene mainScene;
    private Stage mainStage;
    private Parent mainRoot;

    /**
     * initializes the combo box to a list of all months, and the type combo to a list of all types
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList months = FXCollections.observableArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        ObservableList apptTypes = FXCollections.observableArrayList();
        apptTypes.add("Planning Session");
        apptTypes.add("De-Briefing");
        apptTypes.add("Training");
        apptTypes.add("Munch and Mingle");

        monthCombo.setItems(months);
        typeCombo.setItems(apptTypes);

    }

    /**
     * Redirect the user to the main screen on back button click.
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
    /** takes input from user and counts the appointments of that type and month.*/
    public void handleSearchButton(ActionEvent actionEvent) {
        int i = 0;
        String month = monthCombo.getValue().toString().toUpperCase(Locale.ROOT);
        String type = typeCombo.getValue().toString();
        int year = LocalDateTime.now().getYear();
        for (Appointment appointment : Appointment.getAllAppointments()) {
            String aptMonth = appointment.getStartDateTime().getMonth().toString();
            int aptYear = appointment.getStartDateTime().getYear();
            System.out.println(aptMonth);
            String aptType = appointment.getType();
            System.out.println(aptType);
            System.out.println(month);
            if (aptType.equals(type) && month.equals(aptMonth) && aptYear == year) {

                i = i + 1;

            }
        }
        label.setText(String.valueOf(i));
    }
}
