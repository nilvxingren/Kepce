package tr.com.kepce.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

public class Locator {

    private static final Locator INSTANCE = new Locator();

    private Location mLocation;

    private Locator() {
    }

    public static Locator getInstance() {
        return INSTANCE;
    }

    public void start(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    @Nullable
    public Location getLocation() {
        return mLocation;
    }
}
