package tr.com.kepce.common;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagedList<T> {

    @SerializedName("page")
    public int page;
    @SerializedName("size")
    public int countPerPage;
    @SerializedName("count")
    public int totalCount;
    @SerializedName("response")
    public List<T> list;
}
