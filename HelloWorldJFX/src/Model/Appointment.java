package Model;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * An Appointment class that defines the appointment object, gets and sets attributes. Also
 * contains methods that add and delete appointments from allAppointments list.
 */
public class Appointment {
    private int apptId;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;
    private int userId;
    private String type;

    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public Appointment(int apptId, String title, String description, String location, int contactId, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId) {
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;

    }

    public int getApptId() {
        return apptId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getLocation() {
        return location;
    }
    public int getContactId() {
        return contactId;
    }
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public int getCustomerId() {
        return customerId;
    }
    public int getUserId() {
        return userId;
    }
    public String getType() {
        return type;
    }

    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ObservableList<Appointment> getAllAppointments(){
        allAppointments = DBAppointments.getAllAppointments();
        return allAppointments;
    }

    public static void addAppointment(Appointment appointment) throws SQLException {
        int apptId = DBAppointments.insertAppointment(appointment);
        Appointment newappointment = new Appointment(apptId, appointment.getTitle(), appointment.getDescription(), appointment.getLocation(), appointment.getContactId(), appointment.getType(), appointment.getStartDateTime(), appointment.getEndDateTime(), appointment.getCustomerId(), appointment.getUserId());
        allAppointments.add(newappointment);

    }


    public static void deleteAppointment(Appointment appointment) throws SQLException {
        if(allAppointments.contains(appointment)) {
            allAppointments.remove(appointment);
        }
    }



    public static void updateAppointment(Appointment appointment) throws SQLException {
        DBAppointments.updateAppointment(appointment);

    }


    /**
     * getAppointmentTimes method that returns a list of times.
     */
    public static ObservableList getAppointmentTimes(){
        //creating a list of times
        ObservableList times = FXCollections.observableArrayList();
        times.add("00:00");
        times.add("00:30");
        times.add("01:00");
        times.add("01:30");
        times.add("02:00");
        times.add("02:30");
        times.add("03:00");
        times.add("03:30");
        times.add("04:00");
        times.add("04:30");
        times.add("05:00");
        times.add("05:30");
        times.add("06:00");
        times.add("06:30");
        times.add("07:00");
        times.add("07:30");
        times.add("08:00");
        times.add("08:30");
        times.add("09:00");
        times.add("09:30");
        times.add("10:00");
        times.add("10:30");
        times.add("11:00");
        times.add("11:30");
        times.add("12:00");
        times.add("12:30");
        times.add("13:00");
        times.add("13:30");
        times.add("14:00");
        times.add("14:30");
        times.add("15:00");
        times.add("15:30");
        times.add("16:00");
        times.add("16:30");
        times.add("17:00");
        times.add("17:30");
        times.add("18:00");
        times.add("18:30");
        times.add("19:00");
        times.add("19:30");
        times.add("20:00");
        times.add("20:30");
        times.add("21:00");
        times.add("21:30");
        times.add("22:00");
        times.add("22:30");
        times.add("23:00");
        times.add("23:30");
        times.add("24:00");

        return times;
    }

    public static ObservableList apptTypes = FXCollections.observableArrayList();

    /**
     * getAppointmentTypes method that returns a list of appointment types
     * @return
     */
    public static ObservableList getAppointmentTypes(){
        apptTypes.add("Planning Session");
        apptTypes.add("De-Briefing");
        apptTypes.add("Training");
        apptTypes.add("Munch and Mingle");

        return apptTypes;
    }
}
