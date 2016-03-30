package tr.com.kepce.common;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Kepce {

    public static final String ACCOUNT_TYPE = "tr.com.kepce";
    public static final String AUTH_TOKEN_TYPE = "kepce";

    private static final Kepce INSTANCE = new Kepce();
    private static final String BASE_URL = "http://kpc.eu-1.evennode.com/api/";

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
        return getAuthToken(context, false);
    }

    public static String getAuthToken(Context context) {
        return getAuthToken(context, true);
    }

    private static String getAuthToken(Context context, boolean createIfMissing) {
        if (INSTANCE.mAuthToken == null) {
            AccountManager accountManager = AccountManager.get(context);
            Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
            if (accounts.length > 0) {
                INSTANCE.mAuthToken = accountManager.peekAuthToken(accounts[0], Kepce.AUTH_TOKEN_TYPE);
            }
        }

        return INSTANCE.mAuthToken;
    }
}
