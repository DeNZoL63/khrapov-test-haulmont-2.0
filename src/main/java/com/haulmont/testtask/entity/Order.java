package com.haulmont.testtask.entity;

import java.util.Date;

public class Order extends Document_Abstract {
    Long client;
    Long mechanic;
    Date finishDate;
    Double price;
    OrderStatus_Constant orderStatus;
    Long id;

    public Order(Date createDate, String detail, Long client, Long mechanic, Date finishDate, Double price, OrderStatus_Constant orderStatus){
        super(createDate, detail);
        setPrice(price);
        setClient(client);
        setMechanic(mechanic);
        setOrderStatus(orderStatus);
        setFinishDate(finishDate);
    }

    public Order() {
        super();
    }

    public void setPrice(Double accum) {
        this.price = accum;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public void setMechanic(Long mechanic) {
        this.mechanic = mechanic;
    }

    public void setFinishDate(Date workOver) {
        this.finishDate = workOver;
    }

    public void setOrderStatus(OrderStatus_Constant orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public String getOrderStatus() {
        return orderStatus.toString();
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public Long getClient() {
        return client;
    }

    public Long getMechanic() {
        return mechanic;
    }

    public Long getId() {
        return id;
    }
}
