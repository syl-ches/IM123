public class ServiceTarget {
    private int serviceId;
    private int beneficiaryId;

    public ServiceTarget() {}

    public ServiceTarget(int serviceId, int beneficiaryId) {
        this.serviceId = serviceId;
        this.beneficiaryId = beneficiaryId;
    }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public int getBeneficiaryId() { return beneficiaryId; }
    public void setBeneficiaryId(int beneficiaryId) { this.beneficiaryId = beneficiaryId; }
}