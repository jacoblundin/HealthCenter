package model;

import controller.MenuController;

import java.sql.*;

public class UserManager {

    MenuController controller;
    Connection conn;

    private Patient loggedInPatient;
    private Doctor selectedDoctor;
    private boolean doctorSelected = false;

    public UserManager(MenuController controller, Connection conn) {

        this.controller = controller;
        this.conn = conn;
    }

    //Checks for existing IDs in database
    public boolean doesAdminExist(int adminId) {

        boolean adminExists = false;
        try {
            String findAdmin = "{call admin_sign_in (?, ?)}";
            CallableStatement stmt = conn.prepareCall(findAdmin);

            stmt.setInt(1, adminId);
            stmt.registerOutParameter(2, Types.VARCHAR);

            stmt.execute();

            String result = stmt.getString(2);
            if (result.equals("1")) {
                adminExists = true;
            } else {
                adminExists = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adminExists;
    }
    public boolean doesDoctorExist(int employeeId) {

        boolean doctorExists = false;

        try {
            String findDoctor = "{call doctor_sign_in(?, ?)}";
            CallableStatement stmt = conn.prepareCall(findDoctor);

            stmt.setInt(1, employeeId);
            stmt.registerOutParameter(2, Types.VARCHAR);

            stmt.execute();

            String result = stmt.getString(2);
            if (result.equals("1")) {
                setSelectedDoctor(employeeId);
                doctorExists = true;
            } else {
                doctorExists = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorExists;
    }
    public boolean doesPatientExist(int medicalId) {

        boolean patientExists = false;

        try {
            String findPatient = "{call patient_sign_in (?, ?)}";
            CallableStatement stmt = conn.prepareCall(findPatient);

            stmt.setInt(1, medicalId);
            stmt.registerOutParameter(2, Types.VARCHAR);

            stmt.execute();

            String result = stmt.getString(2);
            if (result.equals("1")) {
                setLoggedInPatient(medicalId);
                patientExists = true;
            } else {
                patientExists = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientExists;
    }

    //Patient commands
    public void addPatient(String[] newPatientDetails) {

        int medicalId = 0;
        try {
            String query = "{call Clinic.dbo.register_patient( ?, ?, ? ,?, ?, ?)}";

            CallableStatement statement = conn.prepareCall(query);

            statement.setString(1, newPatientDetails[0]);
            statement.setString(2, newPatientDetails[1]);
            statement.setString(3, newPatientDetails[2]);
            statement.setString(4, newPatientDetails[3]);
            statement.setString(5, newPatientDetails[4]);
            statement.setString(6, newPatientDetails[5]);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                medicalId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setLoggedInPatient(medicalId);
    }
    public void updatePatient() {

        boolean success = false;

        try {
            String query = "{call update_patient_info( ?, ?, ? ,?, ?, ?, ?, ?)}";

            CallableStatement statement = conn.prepareCall(query);

            statement.setInt(1, loggedInPatient.getMedicalId());
            statement.setString(2, loggedInPatient.getfName());
            statement.setString(3, loggedInPatient.getlName());
            statement.setString(4, loggedInPatient.getGender());
            statement.setString(5, loggedInPatient.getAddress());
            statement.setString(6, loggedInPatient.getPhoneNumber());
            statement.setString(7, loggedInPatient.getBirthDate());
            statement.registerOutParameter(8, Types.VARCHAR);

            statement.execute();

            String result = statement.getString(8);
            if (result.equals("-1")) {
                success = false;
            } else {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String showPatientDetails() {

        return loggedInPatient.createPatientInfoString();
    }
    public String getDoctors() {

        String doctorsAndSpecials = "";
        try {
            String query = "{call show_doctor_and_specialization()}";
            CallableStatement statement = conn.prepareCall(query);

            ResultSet resultSet = statement.executeQuery();
            doctorsAndSpecials = buildResultsString(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorsAndSpecials;
    }
    public String searchDoctorsWithSpecial(String special) {

        String doctorsWithSpecials = "";
        try {
            String query = "{call search_for_doctor_with_specialization(?)}";
            CallableStatement statement = conn.prepareCall(query);

            statement.setString(1, special);

            ResultSet resultSet = statement.executeQuery();
            doctorsWithSpecials = buildResultsString(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorsWithSpecials;

    }
    public String getAvailableTimes() {

        if(!doctorSelected){
            return "No doctor selected, please select a doctor first";
        }
        else {
            String availableTimes = "";
            try {
                String query = "{call show_available_times_for_doctor(?)}";
                CallableStatement statement = conn.prepareCall(query);

                statement.setInt(1, selectedDoctor.getEmployeeId());

                ResultSet resultSet = statement.executeQuery();
                availableTimes = buildResultsString(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return availableTimes;
        }

    }

    //Sentinels for current user information
    public String getSelectedDoctorSimple() {

        String doctorInfo = "";
        if (!doctorSelected){
            doctorInfo = "****No doctor currently selected****";
        }
        else {
            doctorInfo = String.format("Selected Doctor: Dr. %s the %s", selectedDoctor.getlName(), selectedDoctor.getSpecialization());
        }
        return doctorInfo;
    }
    public String getLoggedInPatientSimple() {

        String patientInfo = String.format("Logged in as: %d %s %s", loggedInPatient.getMedicalId(), loggedInPatient.getfName(), loggedInPatient.getlName());
        return patientInfo;
    }
    public Patient getLoggedInPatient() {

        return loggedInPatient;
    }
    public Doctor getSelectedDoctor(){

        return selectedDoctor;
    }
    public boolean setSelectedDoctor(int employeeId) {

        try {
            String query = "{call select_doctor(?)}";
            CallableStatement statement = conn.prepareCall(query);

            statement.setInt(1, employeeId);

            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                String fName = (resultSet.getString(2));
                String lName = (resultSet.getString(3));
                String specialization = resultSet.getString(4);
                String phoneNumber = resultSet.getString(5);
                selectedDoctor = new Doctor(employeeId, fName, lName, specialization, phoneNumber);
                doctorSelected = true;
            }
        } catch (SQLException e) {
            doctorSelected = false;
            e.printStackTrace();
        }
        return doctorSelected;
    }
    private void setLoggedInPatient(int medicalId) {
        try {
            String query = "{call view_patient_info(?)}";
            CallableStatement statement = conn.prepareCall(query);

            statement.setInt(1, medicalId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String fName = (resultSet.getString(2));
                String lName = (resultSet.getString(3));
                String gender = resultSet.getString(4);
                String address = resultSet.getString(5);
                String phoneNumber = resultSet.getString(6);
                String birthDate = resultSet.getString(7);
                loggedInPatient = new Patient(medicalId, fName, lName, gender, address, phoneNumber, birthDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Admin commands
    public void addDoctor(String[] newDoctorInfo){

        int employeeeId = 0;
        try {
            String query = "{call insert_doctor( ?, ?, ? ,?)}";

            CallableStatement statement = conn.prepareCall(query);

            statement.setString(1, newDoctorInfo[0]);
            statement.setString(2, newDoctorInfo[1]);
            statement.setString(3, newDoctorInfo[2]);
            statement.setString(4, newDoctorInfo[3]);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                employeeeId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setSelectedDoctor(employeeeId);
    }
    public boolean removeDoctor(int employeeId) {

        boolean success = false;

        try {
            String findDoctor = "{call remove_doctor (?, ?)}";
            CallableStatement stmt = conn.prepareCall(findDoctor);

            stmt.setInt(1, employeeId);
            stmt.registerOutParameter(2, Types.VARCHAR);

            stmt.execute();

            String result = stmt.getString(2);
            if (result.equals("1")) {
                success = true;
            } else {
                success = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
    public void addSpecial(String special, double cost) {

        try {
            String query = "{call insert_specialization( ?, ?)}";

            CallableStatement statement = conn.prepareCall(query);

            statement.setString(1, special);
            statement.setDouble(2, cost);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getPatientList() {

        String patientList = "";
        try {
            String query = "select * from patient;";
            CallableStatement statement = conn.prepareCall(query);

            ResultSet resultSet = statement.executeQuery();
            patientList = buildResultsString(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientList;
    }
    public String getCostList() {

        String costList = "";
        try {
            String query = "{call show_patient_costs()}";
            CallableStatement statement = conn.prepareCall(query);

            ResultSet resultSet = statement.executeQuery();
            costList = buildResultsString(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return costList;
    }
    public String getRecord(int medicalId) {


        String medicalRecord = "";
        try {
            String query = "{call show_medical_record(?)}";
            CallableStatement statement = conn.prepareCall(query);
            
            statement.setInt(1, medicalId);

            ResultSet resultSet = statement.executeQuery();
            medicalRecord = buildResultsString(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicalRecord;
    }

    //Return a results set as a string
    public String buildResultsString(ResultSet resultSet) {

        String resultsString = "";
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for(int i = 1; i <= columnCount; i++){
                String columnName = rsmd.getColumnName(i);
                String output =  String.format("%17s", columnName);
                resultsString += (output + "\t");
            }
            resultsString += '\n';
            while (resultSet.next()) {
                for(int i = 1; i <= columnCount; i++){
                    String columnValue = resultSet.getString(i);
                    String output =  String.format("%17s", columnValue);
                    resultsString += (output + "\t");
                }
                resultsString += '\n';
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultsString;
    }

    public boolean bookAppointment(String date, String time) {

        boolean success = false;
        try {
            String query = "{call add_appointment( ?, ?, ? ,?)}";

            CallableStatement statement = conn.prepareCall(query);

            statement.setInt(1, getSelectedDoctor().getEmployeeId());
            statement.setInt(2, getLoggedInPatient().getMedicalId());
            statement.setString(3, date);
            statement.setString(4, time);

            statement.execute();
            success = true;
            statement.close();

        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        }
        if(success)
        {
            try {
                String query = "{call remove_available_time( ?, ?, ?)}";

                CallableStatement statement = conn.prepareCall(query);

                statement.setInt(1, getSelectedDoctor().getEmployeeId());
                statement.setString(2, date);
                statement.setString(3, time);

                statement.execute();
                success = true;
                statement.close();

            } catch (SQLException e) {
                success = false;
                e.printStackTrace();
            }
        }
        return success;
    }
}