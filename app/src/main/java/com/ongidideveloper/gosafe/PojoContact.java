package com.ongidideveloper.gosafe;

public class PojoContact
{
    private String PhoneNumber,phoneName,personEmail;

    public PojoContact(String phoneName,String phoneNumber, String personEmail) {

        PhoneNumber = phoneNumber;
        this.phoneName = phoneName;
        this.personEmail = personEmail;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }
}
