package tr.com.kepce.common;

public class ServerException extends Exception {

    private int mCode;

    public ServerException(int code) {
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }

}
