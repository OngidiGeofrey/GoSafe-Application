package com.ongidideveloper.gosafe;

public class Victim {

    private String latitude, longitude, link,addressLine, County;

    public Victim() {
    }

    public Victim(String latitude, String longitude, String link, String addressLine, String county) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.link = link;
        this.addressLine = addressLine;
        County = county;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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
}
