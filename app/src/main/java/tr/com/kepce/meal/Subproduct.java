package tr.com.kepce.meal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Subproduct implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private float price;
    @SerializedName("calorie")
    private int calories;

    public Subproduct() {
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeFloat(this.price);
        dest.writeInt(this.calories);
    }

    protected Subproduct(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.price = in.readFloat();
        this.calories = in.readInt();
    }

    public static final Parcelable.Creator<Subproduct> CREATOR = new Parcelable.Creator<Subproduct>() {
        @Override
        public Subproduct createFromParcel(Parcel source) {
            return new Subproduct(source);
        }

        @Override
        public Subproduct[] newArray(int size) {
            return new Subproduct[size];
        }
    };
}
