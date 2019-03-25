package com.haulmont.testtask.entity;

public class Mechanic extends Person_Abstract {
    private double rate;
    private String viewForChoose;

    public Mechanic(String firstName, String secondName, String patronymic, double rate) {
        super(firstName, secondName, patronymic);
        this.rate = rate;
    }

    public Mechanic() {

    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public void setViewForChoose(String viewForChoose){
        this.viewForChoose = viewForChoose;
    }

    public void generateViewForChoose(){
        this.viewForChoose = super.getSecondName() + " " + super.getFirstName() + " " + super.getPatronymic();
    }

    public String getViewForChoose() {
        return viewForChoose;
    }

}
