package com.stivoo.wagba.pojo;

public class MealModel {

    String name;
    String img;
    String price;
    String description;

    public MealModel(String name, String img, String price, String description) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.description = description;
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

    @Override
    public String toString() {
        return "MealModel{" +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
