package tr.com.kepce.meal;

import com.google.gson.annotations.SerializedName;

public class Favorite {

    @SerializedName("id")
    private String id;
    @SerializedName("meal")
    private Meal meal;

    public Meal getMeal() {
        return meal;
    }
}
