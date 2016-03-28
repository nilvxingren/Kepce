package tr.com.kepce.restaurant;

import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("geo")
    private Double[] location;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phoneNumber;
    @SerializedName("minimumAmount")
    private Float minPriceForDelivery;
    @SerializedName("kmAmount")
    private Float minPricePerKm;
    @SerializedName("type")
    private Integer type;
    @SerializedName("carbonFootprint")
    private Float co2e;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double[] getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Float getMinPriceForDelivery() {
        return minPriceForDelivery;
    }

    public Float getMinPricePerKm() {
        return minPricePerKm;
    }

    public Integer getType() {
        return type;
    }

    public Float getCo2e() {
        return co2e;
    }
}
