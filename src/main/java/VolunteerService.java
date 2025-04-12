import java.util.Date;

public class VolunteerService {
    private int serviceId;
    private String serviceLocation;
    private String serviceType;
    private Date startDate;
    private Date endDate;
    private String status;
    private int volunteerId;

    public VolunteerService() {}

    public VolunteerService(int serviceId, String serviceLocation, String serviceType,
                            Date startDate, Date endDate, String status, int volunteerId) {
        this.serviceId = serviceId;
        this.serviceLocation = serviceLocation;
        this.serviceType = serviceType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.volunteerId = volunteerId;
    }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public String getServiceLocation() { return serviceLocation; }
    public void setServiceLocation(String serviceLocation) { this.serviceLocation = serviceLocation; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getVolunteerId() { return volunteerId; }
    public void setVolunteerId(int volunteerId) { this.volunteerId = volunteerId; }
}