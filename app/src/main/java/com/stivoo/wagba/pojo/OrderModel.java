package com.stivoo.wagba.pojo;

import java.util.ArrayList;

public class OrderModel {
    String gate;
    String orderDate;
    String orderInfo;
    String orderTime;
    String period;
    ArrayList<CartItem> meals;
    String id;
    String statusConfirm;
    String statusCooking;

    public void setStatusConfirm(String statusConfirm) {
        this.statusConfirm = statusConfirm;
    }

    public void setStatusCooking(String statusCooking) {
        this.statusCooking = statusCooking;
    }

    public void setStatusDelivery(String statusDelivery) {
        this.statusDelivery = statusDelivery;
    }

    public void setStatusProcess(String statusProcess) {
        this.statusProcess = statusProcess;
    }

    public String getStatusConfirm() {
        return statusConfirm;
    }

    public String getStatusCooking() {
        return statusCooking;
    }

    public String getStatusDelivery() {
        return statusDelivery;
    }

    public String getStatusProcess() {
        return statusProcess;
    }

    String statusDelivery;
    String statusProcess;

    public String getGate() {
        return gate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public OrderModel() {
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setMeals(ArrayList<CartItem> meals) {
        this.meals = meals;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getPeriod() {
        return period;
    }

    public ArrayList<CartItem> getMeals() {
        return meals;
    }

    public String getId() {
        return id;
    }

    public OrderModel(String gate, String orderDate, String orderInfo, String orderTime, String period, ArrayList<CartItem> meals, String id, String statusConfirm, String statusCooking, String statusDelivery, String statusProcess) {
        this.gate = gate;
        this.orderDate = orderDate;
        this.orderInfo = orderInfo;
        this.orderTime = orderTime;
        this.period = period;
        this.meals = meals;
        this.id = id;
        this.statusConfirm = statusConfirm;
        this.statusCooking = statusCooking;
        this.statusDelivery = statusDelivery;
        this.statusProcess = statusProcess;
    }
}
