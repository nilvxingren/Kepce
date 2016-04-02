package tr.com.kepce.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tr.com.kepce.cart.CartEntity;
import tr.com.kepce.restaurant.Restaurant;

public class Order implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.restaurant, flags);
        dest.writeString(this.paymentType);
        dest.writeTypedList(meals);
        dest.writeFloat(price);
    }

    protected Order(Parcel in) {
        this.id = in.readString();
        this.restaurant = in.readParcelable(Restaurant.class.getClassLoader());
        this.paymentType = in.readString();
        this.meals = in.createTypedArrayList(CartEntity.CREATOR);
        this.price = in.readFloat();
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
