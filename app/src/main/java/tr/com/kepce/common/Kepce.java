package tr.com.kepce.common;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Kepce {

    public static final String ACCOUNT_TYPE = "tr.com.kepce";
    public static final String AUTH_TOKEN_TYPE = "kepce";

    private static final Kepce INSTANCE = new Kepce();
    public static final String BASE_URL = "http://bikepce.eu-1.evennode.com/api/";
    public static final String IMAGE_BASE_URL = "http://bikepce.eu-1.evennode.com";

    private KepceService mKepceService;
    private String mAuthToken;

    private Kepce() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();
        mKepceService = retrofit.create(KepceService.class);
    }

    public static KepceService getService() {
        return INSTANCE.mKepceService;
    }

    public static String peekAuthToken(Context context) {
        if (INSTANCE.mAuthToken == null) {
            AccountManager accountManager = AccountManager.get(context);
            Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
            if (accounts.length > 0) {
                INSTANCE.mAuthToken = accountManager.peekAuthToken(accounts[0], Kepce.AUTH_TOKEN_TYPE);
            }
        }
        return INSTANCE.mAuthToken;
    }

    public static String invalidateAuthToken(Context context) {
        if (INSTANCE.mAuthToken == null) {
            return null;
        }
        String token = INSTANCE.mAuthToken;
        AccountManager manager = AccountManager.get(context);
        manager.invalidateAuthToken(Kepce.ACCOUNT_TYPE, token);
        INSTANCE.mAuthToken = null;
        return token;
    }
}
