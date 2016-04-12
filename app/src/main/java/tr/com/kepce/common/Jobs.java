package tr.com.kepce.common;

import android.content.Context;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;

public class Jobs {

    private static final Jobs INSTANCE = new Jobs();

    public static final int PRIORITY_MIN = 1;
    public static final int PRIORITY_SYNC = 2;
    public static final int PRIORITY_PREFETCH = 3;
    public static final int PRIORITY_UX = 4;

    private JobManager mManager;

    private Jobs() {
    }

    public static void init(Context context) {
        INSTANCE.mManager = new JobManager(new Configuration.Builder(context).build());
    }

    public static JobManager getManager() {
        return INSTANCE.mManager;
    }
}
