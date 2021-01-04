package model;

public class Doctor extends Person{

    private int employeeId;
    private String specialization;
    private String phoneNumber;


    public Doctor(int employeeId, String fName, String lName,  String specialization, String phoneNumber) {

        super(fName, lName);
        this.employeeId = employeeId;
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public String getSpecialization() {
        return specialization;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
