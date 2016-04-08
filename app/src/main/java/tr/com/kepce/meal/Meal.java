package tr.com.kepce.meal;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import tr.com.kepce.common.Kepce;
import tr.com.kepce.restaurant.Restaurant;

public class Meal implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("category")
    private String category;
    @SerializedName("price")
    private float price;
    @SerializedName("calorie")
    private int calories;
    @SerializedName("carbonFootprint")
    private float co2e;
    @SerializedName("picture")
    private Photo photo;
    @SerializedName("available")
    private boolean available;
    @SerializedName("restaurantId")
    private String restaurantId;
    @SerializedName("restaurant")
    private Restaurant restaurant;
    @SerializedName("submeals")
    private List<Subproduct> subproducts;
    @SerializedName("favorite")
    private boolean favorite;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public int getCalories() {
        return calories;
    }

    public float getCo2e() {
        return co2e;
    }

    public Photo getPhoto() {
        return photo;
    }

    public boolean isAvailable() {
        return available;
    }

    public List<Subproduct> getSubproducts() {
        return subproducts;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public boolean isFavorite() {
        return favorite;
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(SimpleDraweeView view, String url) {
        if (url != null) {
            view.setImageURI(Uri.parse(Kepce.IMAGE_BASE_URL + url));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.category);
        dest.writeFloat(this.price);
        dest.writeInt(this.calories);
        dest.writeFloat(this.co2e);
        dest.writeByte(available ? (byte) 1 : (byte) 0);
        dest.writeString(this.restaurantId);
        dest.writeByte(favorite ? (byte) 1 : (byte) 0);
        dest.writeTypedList(subproducts);
        dest.writeParcelable(this.photo, flags);
        dest.writeParcelable(this.restaurant, flags);
    }

    public Meal() {
    }

    protected Meal(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.category = in.readString();
        this.price = in.readFloat();
        this.calories = in.readInt();
        this.co2e = in.readFloat();
        this.available = in.readByte() != 0;
        this.restaurantId = in.readString();
        this.favorite = in.readByte() != 0;
        this.subproducts = in.createTypedArrayList(Subproduct.CREATOR);
        this.photo = in.readParcelable(Photo.class.getClassLoader());
        this.restaurant = in.readParcelable(Restaurant.class.getClassLoader());
    }

    public static final Parcelable.Creator<Meal> CREATOR = new Parcelable.Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel source) {
            return new Meal(source);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };
}
