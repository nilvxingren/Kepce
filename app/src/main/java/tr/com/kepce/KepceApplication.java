package tr.com.kepce;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class KepceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
