public class Requestor {
    private int requestorId;
    private String requestorName;
    private String phoneNumber;
    private String requestorEmail;
    private String requestorPass;

    public Requestor() {}

    public Requestor(int requestorId, String requestorName, String phoneNumber,
                     String requestorEmail, String requestorPass) {
        this.requestorId = requestorId;
        this.requestorName = requestorName;
        this.phoneNumber = phoneNumber;
        this.requestorEmail = requestorEmail;
        this.requestorPass = requestorPass;
    }

    public int getRequestorId() { return requestorId; }
    public void setRequestorId(int requestorId) { this.requestorId = requestorId; }

    public String getRequestorName() { return requestorName; }
    public void setRequestorName(String requestorName) { this.requestorName = requestorName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getRequestorEmail() { return requestorEmail; }
    public void setRequestorEmail(String requestorEmail) { this.requestorEmail = requestorEmail; }

    public String getRequestorPass() { return requestorPass; }
    public void setRequestorPass(String requestorPass) { this.requestorPass = requestorPass; }
}
