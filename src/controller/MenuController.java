package controller;

import model.*;
import view.*;

import java.sql.*;

public class MenuController {

    Connection conn;
    MainView view;
    UserManager model;

    public MenuController(Connection conn){

        this.conn = conn;
        view = new MainView();
        model = new UserManager(this, conn);
        showLoginMenu();
    }

    public void showLoginMenu() {

        boolean finished = false;
        while (!finished) {
            int choice = view.loginMenu();
            switch (choice){
                case 1:
                    checkAdminId(view.getEmployeeId());
                    break;
                case 2:
                    checkDoctorId(view.getEmployeeId());
                    break;
                case 3:
                    patientLogin(view.registerOrLoginMenu());
                    break;
                default:
                    finished = true;
            }
        }
    }

    private void checkAdminId(int adminId) {

        if (model.doesAdminExist(adminId)){
            showAdminMenu();
        }
        else{
            loginFailed();
        }
    }
    private void checkDoctorId(int employeeId) {
        if (model.doesDoctorExist(employeeId)){
            showDoctorMenu();
        }
        else{
            loginFailed();
        }
    }
    public void patientLogin(int choice){

        if(choice == 1) {
            if(model.doesPatientExist(view.getMedicalId())){
                showPatientMenu();
            }
            else{
                loginFailed();
                showLoginMenu();
            }
            }
        else if(choice == 2){
            model.addPatient(view.getNewPatientDetails());
            showPatientMenu();
        }
        else{
            showLoginMenu();
        }
    }
    private void loginFailed() {

        view.loginFailed();
        showLoginMenu();
    }

    public void showAdminMenu() {

        boolean finished = false;
        while (!finished) {
        int choice = view.adminMenu();
            switch (choice){
                case 1:
                    model.addDoctor(view.getNewDoctorDetails());
                    break;
                case 2:
                    model.removeDoctor(view.getEmployeeId());
                    break;
                case 3:
                    model.addSpecial(view.getSpecial(), view.getCost());
                    break;
                case 4:
                    view.printString(model.getPatientList());
                    break;
                case 5:
                    view.printString(model.getRecord(view.getMedicalId()));
                    break;
                case 6:
                    view.printString(model.getCostList());
                default:
                    finished = true;
            }
        }
    }
    public void showPatientMenu() {

        boolean finished = false;
        while (!finished) {
            view.printString(model.getLoggedInPatientSimple());
            view.printString(model.getSelectedDoctorSimple());
            int choice = view.patientMenu();
            switch (choice) {
                case 1:
                    view.printString(model.showPatientDetails());
                    break;
                case 2:
                    showUpdatePatientMenu();
                    break;
                case 3:
                    view.printString(model.getDoctors());
                    break;
                case 4:
                    view.printString(model.searchDoctorsWithSpecial(view.getSpecial()));
                    break;
                case 5:
                    if(!model.setSelectedDoctor(view.getEmployeeId())){
                        view.printString("Could not find doctor, please double check ID number");
                    }
                    break;
                case 6:
                    view.printString(model.getSelectedDoctorSimple()+", is available the following times:");
                    view.printString(model.getAvailableTimes());
                    break;
                case 7:
                    checkAppointment();
                    break;
                default:
                    finished = true;
            }
        }
    }

    private void checkAppointment() {

        String date = view.getAppointmentDate();
        String time = view.getAppointmentTime();
        String doctor = model.getSelectedDoctorSimple();

        if(model.bookAppointment(date, time)){
            view.printString(String.format("Appointment booked! %s at %s with %s", date, time, doctor));
        }
        else{
            view.printString("Booking failed. Please try again");
        }

    }

    private void showUpdatePatientMenu() {

        boolean finished = false;
        while (!finished) {
            view.printString(model.getLoggedInPatientSimple());
            int choice = view.updatePatientMenu();
            switch (choice) {
                case 1:
                    model.getLoggedInPatient().setfName(view.getNewAttribute("First Name", model.getLoggedInPatient().getfName()));
                    break;
                case 2:
                    model.getLoggedInPatient().setlName(view.getNewAttribute("Last Name", model.getLoggedInPatient().getlName()));
                    break;
                case 3:
                    model.getLoggedInPatient().setGender(view.getNewAttribute("Gender", model.getLoggedInPatient().getGender()));
                    break;
                case 4:
                    model.getLoggedInPatient().setAddress(view.getNewAttribute("Address", model.getLoggedInPatient().getAddress()));
                    break;
                case 5:
                    model.getLoggedInPatient().setPhoneNumber(view.getNewAttribute("Phone Number", model.getLoggedInPatient().getPhoneNumber()));
                    break;
                case 6:
                    model.getLoggedInPatient().setBirthDate(view.getNewAttribute("BirthDate", model.getLoggedInPatient().getBirthDate()));
                    break;
                default:
                    model.updatePatient();
                    finished = true;
            }
        }
    }

    public void showDoctorMenu() {

        view.printString(model.getSelectedDoctorSimple());
        int choice = view.doctorMenu();
        switch (choice){

            case 1:
            case 2:
            case 3:
            default:
        }
    }
}
