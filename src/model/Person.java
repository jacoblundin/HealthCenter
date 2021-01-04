package model;

public abstract class Person {

    protected String fName;
    protected String lName;

    public Person (String fName, String lName){
        this.fName = fName;
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }
    public String getlName() {
        return lName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }
    public void setlName(String lName) {
        this.lName = lName;
    }

}
