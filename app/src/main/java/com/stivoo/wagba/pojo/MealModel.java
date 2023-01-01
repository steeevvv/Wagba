package com.stivoo.wagba.pojo;

import java.util.ArrayList;

public class MealModel {

    String name;
    String img;
    String price;
    String description;
    int qty;
    String restaurant_name;
    String delivery_fees;
    ArrayList<String> categories;

    public String getDelivery_fees() {
        return delivery_fees;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public MealModel(String name, String img, String price, String description, int qty, String restaurant_name, String delivery_fees, ArrayList<String> categories) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.description = description;
        this.qty = qty;
        this.restaurant_name = restaurant_name;
        this.delivery_fees = delivery_fees;
        this.categories = categories;
    }

    public MealModel() {
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getQty() {
        return qty;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    @Override
    public String toString() {
        return "MealModel{" +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", restaurant_name='" + restaurant_name + '\'' +
                '}';
    }
}
