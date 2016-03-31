package tr.com.kepce.cart;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tr.com.kepce.restaurant.Restaurant;

public class Cart {

    @SerializedName("id")
    private String id;
    @SerializedName("restaurant")
    private Restaurant restaurant;
    @SerializedName("totalPrice")
    private Float totalPrice;
    @SerializedName("calories")
    private Integer calories;
    @SerializedName("co2e")
    private Float co2e;
    @SerializedName("meal")
    private List<CartEntity> entities;

    public String getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public Integer getCalories() {
        return calories;
    }

    public Float getCo2e() {
        return co2e;
    }

    public List<CartEntity> getEntities() {
        return entities;
    }
}
