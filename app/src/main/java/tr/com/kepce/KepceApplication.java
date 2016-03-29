package tr.com.kepce;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.greenrobot.eventbus.EventBus;

import tr.com.kepce.common.KepceEventBusIndex;

public class KepceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary.install(this);
        EventBus.builder().addIndex(new KepceEventBusIndex()).installDefaultEventBus();
        Fresco.initialize(this);
    }
}
