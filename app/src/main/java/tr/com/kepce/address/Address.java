package tr.com.kepce.address;

import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("geo")
    private double[] location;
    @SerializedName("city")
    private String city;
    @SerializedName("district")
    private String district;
    @SerializedName("text")
    private String address;
    @SerializedName("definition")
    private String description;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double[] getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }
}
