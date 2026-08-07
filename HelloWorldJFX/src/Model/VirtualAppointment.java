package Model;

import java.time.LocalDateTime;

public class VirtualAppointment extends Appointment{
    private String virtualAppointment;
    public VirtualAppointment(int apptId, String title, String description, String location, int contactId, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId, String virtualAppointment) {
        super(apptId, title, description, location, contactId, type, startDateTime, endDateTime, customerId, userId);
        this.virtualAppointment = virtualAppointment;
    }

    public String getVirtual() {
        return virtualAppointment;
    }
    public void setVirtualAppointment(String virtualAppointment){
        this.virtualAppointment = virtualAppointment;
    }
}
