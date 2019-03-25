package com.haulmont.testtask.interfaces;

import java.util.Date;

public interface DocumentActivities {

    void setCreateDate(Date createDate);
    void setDetail(String detail);

    Date getCreateDate();
    String getDetail();
}
