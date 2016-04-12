package tr.com.kepce.cart;

import com.google.gson.annotations.SerializedName;

import tr.com.kepce.meal.Meal;

public class CartEntity {

    @SerializedName("quantity")
    private int quantity;
    @SerializedName("meal")
    private Meal meal;

    public int getQuantity() {
        return quantity;
    }

    public Meal getMeal() {
        return meal;
    }
}
