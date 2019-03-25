package com.haulmont.testtask.entity;

import com.haulmont.testtask.interfaces.DocumentActivities;

import java.util.Date;

public abstract class Document_Abstract implements DocumentActivities {
    Date createDate; //дата создания
    String detail; //описание

    Document_Abstract(Date createDate, String detail){
        setCreateDate(createDate);
        setDetail(detail);
    }

    Document_Abstract(){

    }


    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public Date getCreateDate() {
        return this.createDate;
    }

    @Override
    public String getDetail() {
        return this.detail;
    }
}
