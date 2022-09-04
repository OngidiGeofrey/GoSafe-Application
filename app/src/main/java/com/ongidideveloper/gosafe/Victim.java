package com.ongidideveloper.gosafe;

public class Victim {
    private double latitude, longitude;
    private String  link,addressLine, County,date;


    public Victim() {
    }

    public Victim(double latitude, double longitude, String link, String addressLine, String county, String date) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.link = link;
        this.addressLine = addressLine;
        County = county;
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
