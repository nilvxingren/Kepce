package tr.com.kepce.stub;

import android.os.Parcel;

import tr.com.kepce.restaurant.Restaurant;

/**
 * Created by eray on 06/04/16.
 */
public class StubRestaurant extends Restaurant {

    public static Parcel getDataParcel() {
        Parcel dest = Parcel.obtain();
        dest.writeString("stub restoran id");
        dest.writeString("Hocam Piknik");
        dest.writeDoubleArray(new double[] {39.8934891,32.7962548});
        dest.writeString("ODTÜ Çarşı");
        dest.writeString("+905551234567");
        dest.writeFloat(5f);
        dest.writeFloat(1f);
        dest.writeInt(0);
        dest.writeFloat(3.1f);
        return dest;
    }

    public StubRestaurant() {
        super(getDataParcel());
    }
}
