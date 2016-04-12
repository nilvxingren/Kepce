package tr.com.kepce.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tr.com.kepce.cart.CartEntity;
import tr.com.kepce.restaurant.Restaurant;

public class Order {

    @SerializedName("id")
    private String id;
    @SerializedName("restaurant")
    private Restaurant restaurant;
    @SerializedName("paymentType")
    private String paymentType;
    @SerializedName("meals")
    private List<CartEntity> meals;
    @SerializedName("price")
    private float price;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public List<CartEntity> getMeals() {
        return meals;
    }

    public float getPrice() {
        return price;
    }
}
