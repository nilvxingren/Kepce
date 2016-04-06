package tr.com.kepce.stub;

import android.os.Parcel;

import tr.com.kepce.meal.Photo;

/**
 * Created by eray on 06/04/16.
 */
public class StubPhoto extends Photo {

    public static Parcel getDataParcel() {
        Parcel dest = Parcel.obtain();
        dest.writeString("http://www.sosyaltarif.com/wp-content/uploads/2016/03/hunkar-begendi-tarifi.jpg");
        dest.writeString("http://www.sosyaltarif.com/wp-content/uploads/2016/03/hunkar-begendi-tarifi.jpg");
        return dest;
    }

    public StubPhoto() {
        super(getDataParcel());
    }
}
