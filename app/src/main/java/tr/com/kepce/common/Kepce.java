package tr.com.kepce.common;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Kepce {

    private static final Kepce INSTANCE = new Kepce();
    private static final String BASE_URL = "http://kpc.eu-1.evennode.com/api/";

    private KepceService kepceService;

    private Kepce() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient()).build();
        kepceService = retrofit.create(KepceService.class);
    }

    public static KepceService getService() {
        return INSTANCE.kepceService;
    }
}