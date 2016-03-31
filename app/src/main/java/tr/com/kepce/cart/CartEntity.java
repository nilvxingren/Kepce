package tr.com.kepce.cart;

import com.google.gson.annotations.SerializedName;

import tr.com.kepce.meal.Meal;

public class CartEntity {

    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("meal")
    private Meal meal;

    public Integer getQuantity() {
        return quantity;
    }

    public Meal getMeal() {
        return meal;
    }
}
