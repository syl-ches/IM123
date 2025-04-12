public class Beneficiary {
    private int beneficiaryId;
    private String beneficiaryName;
    private String phoneNumber;

    public Beneficiary() {}

    public Beneficiary(int beneficiaryId, String beneficiaryName, String phoneNumber) {
        this.beneficiaryId = beneficiaryId;
        this.beneficiaryName = beneficiaryName;
        this.phoneNumber = phoneNumber;
    }
    public int getBeneficiaryId() { return beneficiaryId; }
    public void setBeneficiaryId(int beneficiaryId) { this.beneficiaryId = beneficiaryId; }

    public String getBeneficiaryName() { return beneficiaryName; }
    public void setBeneficiaryName(String beneficiaryName) { this.beneficiaryName = beneficiaryName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}