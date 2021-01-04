package model;

public class Patient extends Person{


    private int medicalId;
    private String gender;
    private String address;
    private String phoneNumber;
    private String birthDate;

    public Patient(int medicalId, String fName, String lName, String gender, String address, String phoneNumber, String birthDate) {

        super(fName, lName);
        this.medicalId = medicalId;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public int getMedicalId() {
        return medicalId;
    }
    public String getGender() {
        return gender;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getBirthDate() {
        return birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String createPatientInfoString(){

        String patientInfoString = String.format("%s %n%s %n%s %n%s %n%s %n%s %n%s %n",
                "MedicalId: " + medicalId,
                "First name: " + fName,
                "Last name: " + lName,
                "Gender: " + gender,
                "Address: " + address,
                "Phone number: " + phoneNumber,
                "Birth date: " + birthDate);

        return patientInfoString;
    }

}
