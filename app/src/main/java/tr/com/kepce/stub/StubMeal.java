package tr.com.kepce.stub;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import tr.com.kepce.meal.Meal;
import tr.com.kepce.meal.Subproduct;

/**
 * Created by eray on 06/04/16.
 */
public class StubMeal extends Meal {

    public static Parcel getDataParcel() {
        Parcel dest = Parcel.obtain();
        dest.writeString("stub id");
        dest.writeString("Hünkar Beğendi");
        dest.writeInt(0);
        dest.writeFloat(17.5f);
        dest.writeInt(432);
        dest.writeFloat(3.21f);
        dest.writeParcelable(new StubPhoto(), 0);
        dest.writeByte((byte) 1);
        dest.writeString("stub restoran id");
        dest.writeParcelable(new StubRestaurant(), 0);
        dest.writeTypedList(new ArrayList<Subproduct>());
        dest.writeByte((byte) 0);

        dest.setDataPosition(0);

        return dest;
    }

    public StubMeal() {
        super(getDataParcel());
    }

    public static final Parcelable.Creator<StubMeal> CREATOR = new Parcelable.Creator<StubMeal>() {
        @Override
        public StubMeal createFromParcel(Parcel source) {
            return new StubMeal();
        }

        @Override
        public StubMeal[] newArray(int size) {
            return new StubMeal[size];
        }
    };
}
