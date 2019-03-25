package com.haulmont.testtask.entity;

public class MechanicsStatistic extends Mechanic {

    private Integer orders_new;
    private Integer orders_confirm;
    private Integer orders_finish;

    public MechanicsStatistic() {
        super();
    }

    public void setOrders_new(Integer orders_new) {
        this.orders_new = orders_new;
    }

    public void setOrders_confirm(Integer orders_confirm) {
        this.orders_confirm = orders_confirm;
    }

    public void setOrders_finish(Integer orders_finish) {
        this.orders_finish = orders_finish;
    }

    public Integer getOrders_new() {
        return orders_new;
    }

    public Integer getOrders_confirm() {
        return orders_confirm;
    }

    public Integer getOrders_finish() {
        return orders_finish;
    }
}
