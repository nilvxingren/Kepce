package tr.com.kepce.cart;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tr.com.kepce.meal.Meal;

public class CartEntity implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.quantity);
        dest.writeParcelable(this.meal, flags);
    }

    public CartEntity() {
    }

    protected CartEntity(Parcel in) {
        this.quantity = in.readInt();
        this.meal = in.readParcelable(Meal.class.getClassLoader());
    }

    public static final Parcelable.Creator<CartEntity> CREATOR = new Parcelable.Creator<CartEntity>() {
        @Override
        public CartEntity createFromParcel(Parcel source) {
            return new CartEntity(source);
        }

        @Override
        public CartEntity[] newArray(int size) {
            return new CartEntity[size];
        }
    };
}
