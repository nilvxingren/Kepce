package tr.com.kepce.restaurant;

public class Restaurant {

    private String id;
    private String name;
    private int[] location;
    private String address;
    private String phoneNumber;
    private float minPriceForDelivery;
    private float deliveryPricePerDistanceUnit;
    private String type;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int[] getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public float getMinPriceForDelivery() {
        return minPriceForDelivery;
    }

    public float getDeliveryPricePerDistanceUnit() {
        return deliveryPricePerDistanceUnit;
    }

    public String getType() {
        return type;
    }
}
