package tr.com.kepce.meal;

import java.util.List;

public class Meal {

    private String id;
    private String name;
    private String category;
    private float price;
    private int calories;
    private float co2e;
    private String photo;
    private boolean available;
    private List<Subproduct> subproducts;
    private String restaurantId;
    private String restaurantName;

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

    public String getPhoto() {
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

    public String getRestaurantName() {
        return restaurantName;
    }
}
