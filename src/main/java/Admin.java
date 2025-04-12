public class Admin {
    private int adminId;
    private String adminName;
    private String phoneNumber;
    private String adminPass;
    private int serviceId;

    public Admin() {}

    public Admin(int adminId, String adminName, String phoneNumber, String adminPass, int serviceId) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.phoneNumber = phoneNumber;
        this.adminPass = adminPass;
        this.serviceId = serviceId;
    }

    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }

    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) { this.adminName = adminName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAdminPass() { return adminPass; }
    public void setAdminPass(String adminPass) { this.adminPass = adminPass; }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
}