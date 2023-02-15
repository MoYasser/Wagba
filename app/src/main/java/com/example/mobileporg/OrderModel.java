package com.example.mobileporg;

import java.util.ArrayList;

public class OrderModel {
    private String gateTxt;
    private String timeTxt;
    private String phoneNumberTxt;
    private String orderPriceTxt;
    private String statusTxt;
    private ArrayList<String> itemsModelArrayList;

    public OrderModel() {
    }

    public String getGateTxt() {
        return gateTxt;
    }

    public String getStatusTxt() {
        return statusTxt;
    }

    public void setStatusTxt(String statusTxt) {
        this.statusTxt = statusTxt;
    }

    public void setGateTxt(String gateTxt) {
        this.gateTxt = gateTxt;
    }

    public String getTimeTxt() {
        return timeTxt;
    }

    public void setTimeTxt(String timeTxt) {
        this.timeTxt = timeTxt;
    }

    public String getPhoneNumberTxt() {
        return phoneNumberTxt;
    }

    public void setPhoneNumberTxt(String phoneNumberTxt) {
        this.phoneNumberTxt = phoneNumberTxt;
    }

    public String getOrderPriceTxt() {
        return orderPriceTxt;
    }

    public void setOrderPriceTxt(String orderPriceTxt) {
        this.orderPriceTxt = orderPriceTxt;
    }

    public ArrayList<String> getItemsModelArrayList() {
        return itemsModelArrayList;
    }

    public void setItemsModelArrayList(ArrayList<String> itemsModelArrayList) {
        this.itemsModelArrayList = itemsModelArrayList;
    }
}
