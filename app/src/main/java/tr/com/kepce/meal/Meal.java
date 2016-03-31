package tr.com.kepce.meal;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import tr.com.kepce.restaurant.Restaurant;

public class Meal {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("category")
    private Integer category;
    @SerializedName("price")
    private Float price;
    @SerializedName("calorie")
    private Integer calories;
    @SerializedName("carbonFootprint")
    private Float co2e;
    @SerializedName("picture")
    private String photo;
    @SerializedName("available")
    private Boolean available;
    @SerializedName("restaurantId")
    private String restaurantId;
    @SerializedName("restaurant")
    private Restaurant restaurant;
    @SerializedName("submeals")
    private List<Subproduct> subproducts;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCategory() {
        return category;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getCalories() {
        return calories;
    }

    public Float getCo2e() {
        return co2e;
    }

    public String getPhoto() {
        return photo;
    }

    public Boolean isAvailable() {
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

    @BindingAdapter("imageUrl")
    public static void loadImage(SimpleDraweeView view, String url) {
        if (url != null) {
            view.setImageURI(Uri.parse(url));
        }
    }
}
