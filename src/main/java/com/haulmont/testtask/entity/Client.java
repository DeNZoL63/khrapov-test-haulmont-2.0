package com.haulmont.testtask.entity;

public class Client extends Person_Abstract {
    private String phoneNumber;
    private String viewForChoose;

    public Client(){
        super();
    }
    public Client(String firstName, String secondName, String patronymic, String phoneNumber) {
        super(firstName, secondName, patronymic);

        this.phoneNumber = phoneNumber;
        viewForChoose = secondName + " " + firstName + " " + patronymic;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setViewForChoose() {
        this.viewForChoose = super.getSecondName() + " " + super.getFirstName() + " " + super.getPatronymic();
    }

    public String getViewForChoose() {
        return viewForChoose;
    }
}
