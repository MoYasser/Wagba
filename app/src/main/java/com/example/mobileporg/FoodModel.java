package com.example.mobileporg;

public class FoodModel {

    private String itemImage;
    private String itemNameTxt;
    private String availableImage;
    private String itemPriceTxt;
    private String itemCount;

    public FoodModel() {
    }

    public FoodModel(String itemImage, String itemNameTxt, String availableImage, String itemPriceTxt,String itemCount) {
        this.itemImage = itemImage;
        this.itemNameTxt = itemNameTxt;
        this.availableImage = availableImage;
        this.itemPriceTxt = itemPriceTxt;
        this.itemCount = itemCount;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemNameTxt() {
        return itemNameTxt;
    }

    public void setItemNameTxt(String itemNameTxt) {
        this.itemNameTxt = itemNameTxt;
    }

    public String getAvailableImage() {
        return availableImage;
    }

    public void setAvailableImage(String availableImage) {
        this.availableImage = availableImage;
    }

    public String getItemPriceTxt() {
        return itemPriceTxt;
    }

    public void setItemPriceTxt(String itemPriceTxt) {
        this.itemPriceTxt = itemPriceTxt;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }
}
