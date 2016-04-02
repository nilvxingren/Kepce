package tr.com.kepce.cart;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tr.com.kepce.restaurant.Restaurant;

public class Cart implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.restaurant, flags);
        dest.writeFloat(this.totalPrice);
        dest.writeInt(this.calories);
        dest.writeFloat(this.co2e);
        dest.writeTypedList(entities);
    }

    public Cart() {
    }

    protected Cart(Parcel in) {
        this.id = in.readString();
        this.restaurant = in.readParcelable(Restaurant.class.getClassLoader());
        this.totalPrice = in.readFloat();
        this.calories = in.readInt();
        this.co2e = in.readFloat();
        this.entities = in.createTypedArrayList(CartEntity.CREATOR);
    }

    public static final Parcelable.Creator<Cart> CREATOR = new Parcelable.Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}
