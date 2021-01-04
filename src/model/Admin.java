package model;

public class Admin extends Person{

    private int adminId;

    public Admin(int adminId, String fName, String lName) {

        super(fName, lName);
        this.adminId = adminId;
    }

    public int getAdminId() {
        return adminId;
    }
}
