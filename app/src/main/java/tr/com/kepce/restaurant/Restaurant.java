package tr.com.kepce.restaurant;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tr.com.kepce.common.Locator;

public class Restaurant implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("geo")
    private double[] location;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phoneNumber;
    @SerializedName("minimumAmount")
    private float minPriceForDelivery;
    @SerializedName("kmAmount")
    private float minPricePerKm;
    @SerializedName("type")
    private int type;
    @SerializedName("carbonFootprint")
    private float co2e;

    public Restaurant() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double[] getLocation() {
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

    public float getMinPricePerKm() {
        return minPricePerKm;
    }

    public int getType() {
        return type;
    }

    public float getCo2e() {
        return co2e;
    }

    public float getDistance() {
        if (Locator.getInstance().getLocation() == null
                || location == null || location.length != 2) {
            return 0f;
        }
        float[] distance = new float[1];
        Location.distanceBetween(location[0], location[1],
                Locator.getInstance().getLocation().getLatitude(),
                Locator.getInstance().getLocation().getLongitude(),
                distance);
        return distance[0] / 1000f;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeDoubleArray(this.location);
        dest.writeString(this.address);
        dest.writeString(this.phoneNumber);
        dest.writeFloat(this.minPriceForDelivery);
        dest.writeFloat(this.minPricePerKm);
        dest.writeInt(this.type);
        dest.writeFloat(this.co2e);
    }

    protected Restaurant(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.location = in.createDoubleArray();
        this.address = in.readString();
        this.phoneNumber = in.readString();
        this.minPriceForDelivery = in.readFloat();
        this.minPricePerKm = in.readFloat();
        this.type = in.readInt();
        this.co2e = in.readFloat();
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
