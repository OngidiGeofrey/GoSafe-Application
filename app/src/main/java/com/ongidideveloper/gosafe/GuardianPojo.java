package com.ongidideveloper.gosafe;

public class GuardianPojo {

    private String guardianName;
    private String guardianPhone;
    private String personEmail;

    public GuardianPojo() {
    }

    public GuardianPojo(String guardianName, String guardianPhone, String personEmail) {
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.personEmail = personEmail;
    }

    public String getGuardianName() {
        return guardianName;
    }



    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }
}
