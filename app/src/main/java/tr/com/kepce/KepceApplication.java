package tr.com.kepce;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import tr.com.kepce.common.KepceEventBusIndex;
import tr.com.kepce.common.Locator;

public class KepceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary.install(this);
        Locale.setDefault(new Locale("TR"));
        EventBus.builder().addIndex(new KepceEventBusIndex()).installDefaultEventBus();
        Fresco.initialize(this);
        Locator.getInstance().start(this);
    }
}
