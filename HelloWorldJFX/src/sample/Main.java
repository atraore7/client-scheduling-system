package sample;

import DBAccess.DBAppointments;
import Model.Appointment;
import Model.Customer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Main
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/LoginScreen.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 519, 340));
        primaryStage.show();

        System.out.println(ZoneId.systemDefault());
        System.out.println(ZonedDateTime.now());



    }


    public static void main(String[] args) {
        //to test french translation
        // Locale.setDefault(new Locale("fr"));

        //start connection to database
        Database.JDBC.makeConnection();
        launch(args);



        //end connection to database
        Database.JDBC.closeConnection();

    }
}
