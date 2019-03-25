package com.haulmont.testtask.entity;

public class OrderWithFIO extends Order {

    private String clientFio;
    private String mechanicFio;

    public OrderWithFIO() {
        super();
    }

    public void setMechanicFio(String mechanicFio) {
        this.mechanicFio = mechanicFio;
    }

    public void setClientFio(String clientFio) {
        this.clientFio = clientFio;
    }

    public String getMechanicFio() {
        return mechanicFio;
    }

    public String getClientFio() {
        return clientFio;
    }
}
