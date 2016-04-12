package tr.com.kepce.common;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

public abstract class BaseJob extends Job {

    protected BaseJob(Params params) {
        super(params);
    }

    @Override
    public void onAdded() {
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount,
                                                     int maxRunCount) {
        if (throwable instanceof ServerException) {
            return RetryConstraint.CANCEL;
        } else if (throwable instanceof NetworkException) {
            NetworkException ex = (NetworkException) throwable;
            if (!ex.shouldRetry()) {
                return RetryConstraint.CANCEL;
            }
        }
        return RetryConstraint.createExponentialBackoff(runCount, 1000);
    }
}
