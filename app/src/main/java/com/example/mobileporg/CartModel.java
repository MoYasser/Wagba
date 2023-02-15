package com.example.mobileporg;

public class CartModel {
    private String cartImage;
    private String cartTxt;
    private String countTxt;
    private String cartItemPrice;

    public CartModel() {

    }

    public CartModel(String cartImage, String cartTxt, String countTxt) {
        this.cartImage = cartImage;
        this.cartTxt = cartTxt;
        this.countTxt = countTxt;
    }

    public String getCartImage() {
        return cartImage;
    }

    public String getCartItemPrice() {
        return cartItemPrice;
    }

    public void setCartItemPrice(String cartItemPrice) {
        this.cartItemPrice = cartItemPrice;
    }

    public void setCartImage(String cartImage) {
        this.cartImage = cartImage;
    }

    public String getCartTxt() {
        return cartTxt;
    }

    public void setCartTxt(String cartTxt) {
        this.cartTxt = cartTxt;
    }

    public String getCountTxt() {
        return countTxt;
    }

    public void setCountTxt(String countTxt) {
        this.countTxt = countTxt;
    }
}
