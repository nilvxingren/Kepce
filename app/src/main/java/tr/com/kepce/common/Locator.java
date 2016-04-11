package tr.com.kepce.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;

public class Locator {

    private static final Locator INSTANCE = new Locator();
    private static final int REQUEST_CODE = 5;

    private LocationManager mManager;
    private Location mLocation;

    private Locator() {
    }

    public static Locator getInstance() {
        return INSTANCE;
    }

    private void locate() {
        mLocation = mManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (mLocation == null) {
            mManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1L, 0f,
                    new AutoDestroyLocationListener());
        }
    }

    public void start(Activity activity) {
        mManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE);
            }
            return;
        }
        locate();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locate();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Nullable
    public Location getLocation() {
        return mLocation;
    }

    private class AutoDestroyLocationListener implements LocationListener {

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
