package tr.com.kepce.meal;

import com.google.gson.annotations.SerializedName;

public class Subproduct {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private float price;
    @SerializedName("calorie")
    private int calories;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getCalories() {
        return calories;
    }
}
