public class Volunteer {
    private int volunteerId;
    private String volunteerName;
    private String phoneNumber;
    private String volunteerEmail;
    private String volunteerPass;

    public Volunteer() {}

    public Volunteer(int volunteerId, String volunteerName, String phoneNumber,
                     String volunteerEmail, String volunteerPass) {
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.phoneNumber = phoneNumber;
        this.volunteerEmail = volunteerEmail;
        this.volunteerPass = volunteerPass;
    }

    public int getVolunteerId() { return volunteerId; }
    public void setVolunteerId(int volunteerId) { this.volunteerId = volunteerId; }

    public String getVolunteerName() { return volunteerName; }
    public void setVolunteerName(String volunteerName) { this.volunteerName = volunteerName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getVolunteerEmail() { return volunteerEmail; }
    public void setVolunteerEmail(String volunteerEmail) { this.volunteerEmail = volunteerEmail; }

    public String getVolunteerPass() { return volunteerPass; }
    public void setVolunteerPass(String volunteerPass) { this.volunteerPass = volunteerPass; }
}