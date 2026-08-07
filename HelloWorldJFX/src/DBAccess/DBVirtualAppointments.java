package DBAccess;

import Database.JDBC;
import Model.Appointment;
import Model.VirtualAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBVirtualAppointments {
    public static ObservableList<VirtualAppointment> getVirtualAppointments(){
        //creating allVirtualAppointmentsList
        ObservableList<VirtualAppointment> allVirtualAppointmentsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Appointment_ID, Title, Description, Location, Start, End, Customer_ID, User_ID, Contact_ID, Type, Virtual_Or_In_Office\n" +
                    "FROM virtual_appointments;";

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
                String virtual = resultSet.getString("Virtual_Or_In_Office");

                //creating an appointment object for each row that passes through the while loop
                VirtualAppointment appointment = new VirtualAppointment(apptId, title, description, location, contact, type, startTime, endTime, customerId, userId, virtual);
                //adding each appointment to the allAppointmentsList created at the beginning of the method
                allVirtualAppointmentsList.add(appointment);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allVirtualAppointmentsList;

    }
    public static int deleteVirtualAppointment(int apptId) throws SQLException {
        String sql = "DELETE FROM virtual_appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, apptId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    public static int insertVirtualAppointment(VirtualAppointment appointment) throws SQLException {
        String sql = "INSERT INTO virtual_appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID, Virtual_Or_In_Office) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        ps.setString(10, appointment.getVirtual());
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

    public static int updateVirtualAppointment(VirtualAppointment appointment) throws SQLException {
        String sql = "UPDATE virtual_appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
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
