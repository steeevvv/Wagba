package com.stivoo.wagba.pojo;

public class OrderModel {
    String order_date;
    String order_id;

    public String getOrder_date() {
        return order_date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public OrderModel(String order_date, String order_id) {
        this.order_date = order_date;
        this.order_id = order_id;
    }
}
