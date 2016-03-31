package tr.com.kepce.address;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import tr.com.kepce.BR;

public class Address extends BaseObservable {

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

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public double[] getLocation() {
        return location;
    }

    @Bindable
    public String getCity() {
        return city;
    }

    @Bindable
    public String getDistrict() {
        return district;
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setLocation(double[] location) {
        this.location = location;
        notifyPropertyChanged(BR.location);
    }

    public void setCity(String city) {
        this.city = city;
        notifyPropertyChanged(BR.city);
    }

    public void setDistrict(String district) {
        this.district = district;
        notifyPropertyChanged(BR.district);
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }
}
