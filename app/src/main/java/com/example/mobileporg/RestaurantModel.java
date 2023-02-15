package com.example.mobileporg;

import java.util.List;

public class RestaurantModel {

    private String logoImage;
    private String restaurantName;
    private List<FoodModel> foodModelList;

    public RestaurantModel() {
    }

    public RestaurantModel(String logoImage, String restaurantName, List<FoodModel> foodModelList) {
        this.logoImage = logoImage;
        this.restaurantName = restaurantName;
        this.foodModelList = foodModelList;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<FoodModel> getFoodModelList() {
        return foodModelList;
    }

    public void setFoodModelList(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
    }
}
