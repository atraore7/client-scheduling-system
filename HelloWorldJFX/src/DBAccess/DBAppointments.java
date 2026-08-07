package DBAccess;

import Database.JDBC;
import Model.Appointment;
import Model.Customer;
import Model.VirtualAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Database access for getting, updating, and adding appointments
 * within the database.
 */
public class DBAppointments {
    /**
     * getAllAppointments method that gets all appointments out of the database.
     * @return
     */
    public static ObservableList<Appointment> getAllAppointments(){
        //creating allAppointmentsList
        ObservableList<Appointment> allAppointmentsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Appointment_ID, Title, Description, Location, Start, End, Customer_ID, User_ID, Contact_ID, Type\n" +
                    "FROM appointments;";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int apptId = Integer.parseInt(resultSet.getString("Appointment_ID"));
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                int contact = resultSet.getInt("Contact_ID");
                LocalDateTime startTime = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("End").toLocalDateTime();
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                String type = resultSet.getString("Type");

                //creating an appointment object for each row that passes through the while loop
                Appointment appointment = new Appointment(apptId, title, description, location, contact, type, startTime, endTime, customerId, userId);
                //adding each appointment to the allAppointmentsList created at the beginning of the method
                allAppointmentsList.add(appointment);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointmentsList;

    }

    /**
     * deleteAppointments method that deletes appointment from database by appointment id.
     * @param apptId
     * @return
     * @throws SQLException
     */
    public static int deleteAppointment(int apptId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, apptId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * insertAppointment method that inserts appointments into the database
     * @param appointment
     * @return
     * @throws SQLException
     */
    public static int insertAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartDateTime()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getEndDateTime()));
        ps.setInt(7, appointment.getCustomerId());
        ps.setInt(8, appointment.getUserId());
        ps.setInt(9, appointment.getContactId());
        System.out.println(sql);
        boolean rowsAffected = ps.execute();
        PreparedStatement ps1 = JDBC.getConnection().prepareStatement("select last_insert_id()");
        ResultSet rs = ps1.executeQuery();
        int apptId = 0;
        if(rs.next()){
            apptId = rs.getInt(1);
        }

        return apptId;

    }

    /**
     * updateAppointment method that updates appointments in the database by appointmentId
     * @param appointment
     * @return
     * @throws SQLException
     */
    public static int updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        //setting values to the ? in the sql statement
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartDateTime()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getEndDateTime()));
        ps.setInt(7, appointment.getCustomerId());
        ps.setInt(8, appointment.getUserId());
        ps.setInt(9, appointment.getContactId());
        ps.setInt(10, appointment.getApptId());

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

}
