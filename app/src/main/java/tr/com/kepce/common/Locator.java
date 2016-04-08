package tr.com.kepce.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
        if (mLocation == null) {
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1L, 0f,
                    new AutoDestroyLocationListener(manager));
        }
    }

    @Nullable
    public Location getLocation() {
        return mLocation;
    }

    private class AutoDestroyLocationListener implements LocationListener {

        private LocationManager mManager;

        public AutoDestroyLocationListener(LocationManager manager) {
            mManager = manager;
        }

        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
            mManager.removeUpdates(this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}
