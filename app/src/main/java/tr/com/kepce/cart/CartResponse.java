package tr.com.kepce.cart;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tr.com.kepce.restaurant.Restaurant;

public class CartResponse {

    @SerializedName("id")
    private String id;
    @SerializedName("restaurant")
    private Restaurant restaurant;
    @SerializedName("totalPrice")
    private float totalPrice;
    @SerializedName("calories")
    private int calories;
    @SerializedName("co2e")
    private float co2e;
    @SerializedName("meal")
    private List<CartEntity> entities;

    public String getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public int getCalories() {
        return calories;
    }

    public float getCo2e() {
        return co2e;
    }

    public List<CartEntity> getEntities() {
        return entities;
    }
}
