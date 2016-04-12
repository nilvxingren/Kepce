package tr.com.kepce.common;

public class NetworkException extends Exception {

    private int mCode;

    public NetworkException(int code) {
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }

    public boolean shouldRetry() {
        return mCode < 400 || mCode > 499;
    }
}
