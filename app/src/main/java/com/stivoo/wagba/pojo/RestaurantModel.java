package com.stivoo.wagba.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestaurantModel {
    String logo;
    String cover;
    String name;
    String delivery_fees;
    String duration;
    List<String> categories;
    Boolean featured;
    ArrayList<MealModel> meals;

    public String getLogo() {
        return logo;
    }

    public String getCover() {
        return cover;
    }

    public String getName() {
        return name;
    }

    public String getDelivery_fees() {
        return delivery_fees;
    }

    public String getDuration() {
        return duration;
    }

    public Boolean getFeatured(){
        return featured;
    }

    public List<String> getCategories() {
        return categories;
    }

    public ArrayList<MealModel> getMeals() {
        return meals;
    }

    public RestaurantModel(String logo, String cover, String name, String delivery_fees, String duration, List<String> categories, Boolean featured, ArrayList<MealModel> meals) {
        this.logo = logo;
        this.cover = cover;
        this.name = name;
        this.delivery_fees = delivery_fees;
        this.duration = duration;
        this.categories = categories;
        this.featured = featured;
        this.meals = meals;
    }

    public RestaurantModel(){
    }

    @Override
    public String toString() {
        return "RestaurantModel{" +
                "logo='" + logo + '\'' +
                ", cover='" + cover + '\'' +
                ", name='" + name + '\'' +
                ", delivery_fees='" + delivery_fees + '\'' +
                ", duration='" + duration + '\'' +
                ", categories=" + categories +
                ", featured=" + featured +
                ", meals=" + meals +
                '}';
    }
}
