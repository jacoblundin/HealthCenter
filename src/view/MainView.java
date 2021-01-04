package view;

public class MainView {

    public int loginMenu() {

        System.out.printf("%s %n%s %n%s %n%s %n%s %n%s",
                "Which kind of user are you?",
                "1. Admin.",
                "2. Doctor.",
                "3. Patient.",
                "0. Exit.",
                "Choice: ");
        int choice = Utilities.getInteger(0, 3);
        return choice;
    }

    public int adminMenu() {

        System.out.printf("%s %n%s %n%s %n%s %n%s %n%s %n%s %n%s %n%s",
                "What would you like to do?",
                "1. Add Doctor.",
                "2. Remove Doctor.",
                "3. Add Specialization.",
                "4. See list of patients.",
                "5. See medical record.",
                "6. See total visit cost",
                "0. Exit.",
                "Choice: ");
        int choice = Utilities.getInteger(0, 6);
        return choice;
    }
    public int doctorMenu() {

        System.out.printf("%s %n%s %n%s %n%s %n%s %n%s %n%s %n",
                "What would you like to do?",
                "1. View medical record",
                "2. Enter new medical record",
                "3. View upcoming appointments ",
                "4. Change availability",
                "0. Log out",
                "Choice: ");
        int choice = Utilities.getInteger(0, 4);
        return choice;
    }
    public int patientMenu() {

        System.out.printf("%s %n%s %n%s %n%s %n%s %n%s  %n%s %n%s %n%s %n",
                "What do you want to do?",
                "1. View my information.",
                "2. Update my information.",
                "3. Show doctors.",
                "4. Search for specialization.",
                "5. Select doctor.",
                "6. Show available days for selected doctor.",
                "7. Book Appointment with selected doctor.",
                "0. Log Out",
                "Choice: ");
        int choice = Utilities.getInteger(0, 7);
        return choice;
    }

    public int registerOrLoginMenu() {

        System.out.printf("%s %n%s %n%s %n%s %n",
                "1. Login as existing Patient",
                "2. Register as new patient",
                "0. Quit and return to login menu",
                "Choice: ");
        int choice = Utilities.getInteger(0,2);
        return choice;
    }
    public int updatePatientMenu() {

        System.out.printf("%s %n%s %n%s %n%s %n%s %n%s %n%s %n%s",
                "What information do you want to change?",
                "1. First name.",
                "2. Last name.",
                "3. Gender",
                "4. Address.",
                "5. Phone number.",
                "0. Save changed information.",
                "Answer: ");
        int choice = Utilities.getInteger(0,5);
        return choice;
    }

    public void loginFailed() {

        System.out.println("ID not recognised. Login failed");
    }

    public int getMedicalId() {

        System.out.printf("Enter 9-digit Medical ID: ");
        int id = Utilities.getInteger(0, 999999999);
        return id;
    }
    public int getEmployeeId() {

        System.out.printf("Enter 5-digit Employee ID: ");
        int id = Utilities.getInteger(0, 99999);
        return id;
    }

    public String[] getNewDoctorDetails() {

        String[] newDoctorDetails = new String[4];

        System.out.println("First name:");
        newDoctorDetails[0] = Utilities.getString();
        System.out.println("Last name:");
        newDoctorDetails[1] = Utilities.getString();
        System.out.println("Phone number:");
        newDoctorDetails[2] = Utilities.getString();
        System.out.println("Specialization:");
        newDoctorDetails[3] = Utilities.getString();

        return newDoctorDetails;
    }
    public String[] getNewPatientDetails() {

        String[] newPatientDetails = new String[6];

        System.out.println("First name:");
        newPatientDetails[0] = Utilities.getString();
        System.out.println("Last name:");
        newPatientDetails[1] = Utilities.getString();
        System.out.println("Gender:");
        newPatientDetails[2] = Utilities.getString();
        System.out.println("Address:");
        newPatientDetails[3] = Utilities.getString();
        System.out.println("Phone number:");
        newPatientDetails[4] = Utilities.getString();
        System.out.println("Birth date (YYYYMMDD):");
        newPatientDetails[5] = Utilities.getDate();

        return newPatientDetails;
    }

    public String getNewAttribute(String attribute, String current) {
        System.out.printf("Current %s is: %s %n", attribute, current);
        System.out.printf("Enter new %s: ", attribute);
        String newInfo = Utilities.getString();
        return newInfo;
    }

    public String getSpecial() {

        System.out.println("Enter Specialization: ");
        return Utilities.getString();
    }
    public double getCost(){

        System.out.println("Enter Cost: ");
        return Utilities.getDouble();
    }

    public void printString(String string) {

        System.out.println(string);
    }

    public String getAppointmentDate() {

        System.out.printf("Which date? (YYYYMMDD):");
        String choice = Utilities.getDate();
        return choice;
    }

    public String getAppointmentTime() {
        System.out.printf("Which time? (XX:XX)");
        String choice = Utilities.getTime();
        return choice;
    }
}