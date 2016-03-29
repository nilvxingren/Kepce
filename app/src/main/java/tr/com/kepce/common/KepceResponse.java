package tr.com.kepce.common;

import com.google.gson.annotations.SerializedName;

public class KepceResponse<T> {

    @SerializedName("code")
    public int code;
    @SerializedName("data")
    public T data;
}
