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
    String status;

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

    public String getStatus() {
        return status;
    }

    public OrderModel(String gate, String orderDate, String orderInfo, String orderTime, String period, ArrayList<CartItem> meals, String id, String status) {
        this.gate = gate;
        this.orderDate = orderDate;
        this.orderInfo = orderInfo;
        this.orderTime = orderTime;
        this.period = period;
        this.meals = meals;
        this.id = id;
        this.status = status;
    }
}
