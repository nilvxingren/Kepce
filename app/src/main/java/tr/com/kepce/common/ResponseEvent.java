package tr.com.kepce.common;

public class ResponseEvent<T> {

    private KepceResponse<T> mResponse;

    public ResponseEvent(KepceResponse<T> response) {
        mResponse = response;
    }

    public KepceResponse<T> getResponse() {
        return mResponse;
    }
}
