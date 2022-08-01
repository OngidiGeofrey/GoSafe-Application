package com.ongidideveloper.gosafe;

public class Police {
    private String name,location,emergency_contacts;

    public Police(String name, String location, String emergency_contacts) {
        this.name = name;
        this.location = location;
        this.emergency_contacts = emergency_contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
