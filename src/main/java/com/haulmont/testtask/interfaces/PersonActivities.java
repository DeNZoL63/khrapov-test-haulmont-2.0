package com.haulmont.testtask.interfaces;

public interface PersonActivities {

    void setFirstName(String firstName);
    void setPatronymic(String patronymic);
    void setSecondName(String secondName);
    void setId(Long id);

    String getFirstName();
    String getPatronymic();
    String getSecondName();
    Long getId();

}
