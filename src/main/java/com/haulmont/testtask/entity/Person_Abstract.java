package com.haulmont.testtask.entity;

import com.haulmont.testtask.interfaces.PersonActivities;

public abstract class Person_Abstract implements PersonActivities {
    private String firstName; //имя
    private String secondName; //фамилия
    private String patronymic; //отчество
    private Long id; //уникальный идентификатор

    public Person_Abstract(){}

    public Person_Abstract(String firstName, String secondName, String patronymic) {
        setFirstName(firstName);
        setSecondName(secondName);
        setPatronymic(patronymic);
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public String getSecondName() {
        return secondName;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
