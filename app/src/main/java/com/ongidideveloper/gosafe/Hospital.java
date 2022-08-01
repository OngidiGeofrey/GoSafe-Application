package com.ongidideveloper.gosafe;

public class Hospital {
    private String hospital_name,location,emergency_contacts;
    public Hospital() {

    }



    public Hospital(String hospital_name, String location, String emergency_contacts) {
        this.hospital_name = hospital_name;
        this.location = location;
        this.emergency_contacts = emergency_contacts;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmergency_contacts() {
        return emergency_contacts;
    }

    public void setEmergency_contacts(String emergency_contacts) {
        this.emergency_contacts = emergency_contacts;
    }
}
