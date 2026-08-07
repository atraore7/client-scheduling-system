package sample;


import Model.Appointment;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class called LoginController that is a controller for the LoginScreen.
 */

public class LoginController implements Initializable {
    public Button LoginButton;
    public Label label;
    public TextField userNameInput;
    public TextField userPasswordInput;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label loginLabel;
    private Scene scene;
    private Stage stage;
    private Parent root;

    /**
     * verifies user input matches user database information on click login.
     * Either allows or denies access.
     * @param event
     * @return
     * @throws IOException
     */
    public Appointment OnLogin(ActionEvent event) throws IOException {
        //create boolean to set true if login is successful and false if not.
        boolean success = false;
        //for loop through allUsers to compare input to user data.
        for(User user : User.allUsers){
            //get the text from input into variables
            String nameInput = userNameInput.getText();
            String passwordInput = userPasswordInput.getText();

            //if input equals user data then direct to next page, and set boolean success to true.
            if(nameInput.equals(user.getUserName()) && passwordInput.equals(user.getUserPassword())){
                //changes scene to MainScreen on successful login and sets success to true
                root = FXMLLoader.load(getClass().getResource("../fxml/MainScreen.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                success = true;

                LocalDateTime currentDatetime = LocalDateTime.now();
                LocalDateTime timein15 = currentDatetime.plusMinutes(15);
                boolean noAppointments = true;
                for(Appointment appointment : Appointment.getAllAppointments()){
                    LocalDateTime startDateTime = appointment.getStartDateTime();
                    boolean isAptBefore = startDateTime.isBefore(timein15);
                    boolean isAptAfter = startDateTime.isAfter(currentDatetime);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm");
                    String startString = appointment.getStartDateTime().format(formatter);
                    if(isAptBefore == true && isAptAfter == true){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Appointment ID: " + appointment.getApptId() +
                                " Date: " + startString + " UTC is coming up");
                        alert.showAndWait();
                        noAppointments = false;
                    }
                }
                if(noAppointments == true) {
                    Alert newalert = new Alert(Alert.AlertType.CONFIRMATION);
                    newalert.setContentText("There are no upcoming appointments");
                    newalert.showAndWait();
                }
            }
        }
        if(success == true){
            //to print to file login_activity.txt if login is successful
            File file = new File("login_activity.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            String userName = userNameInput.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String date = LocalDateTime.now().format(formatter);
            //printing this line each time a user successfully logs on.
            printWriter.println("User " + userName + " successfully login in at " + date);
            printWriter.close();

        }
        //if success is false
        if(success == false) {
            //display error message if login is unsuccessful in french or english.
            //french error message
            if(Locale.getDefault().toString().equals("fr")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Vous avez entré un nom d’utilisateur ou un mot de passe incorrect.");
                alert.showAndWait();
            }
            //english error message
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("You have enter an incorrect user name or password.");
                alert.showAndWait();
            }
            //write to login_activity file if login fails.
            File file = new File("login_activity.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            String userName = userNameInput.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String date = LocalDateTime.now().format(formatter);
            //printing this line each time a user successfully logs on.
            printWriter.println("User " + userName + " failed to login at " + date);
            printWriter.close();

        }
        return null;
    }

    /**
     * Initializes screen with the french translation, if the users Locale is "fr"
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setting label to ZoneID
        label.setText(String.valueOf(ZoneId.systemDefault()));
        Locale userLocale = Locale.getDefault();
        System.out.println(userLocale);
        if(userLocale.toString().equals("fr")){
            LoginButton.setText("S'identifier");
            loginLabel.setText("S'identifier");
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            loginLabel.prefWidth(150);

        }
    }
}
