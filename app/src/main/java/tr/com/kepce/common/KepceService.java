package tr.com.kepce.common;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tr.com.kepce.profile.User;

public interface KepceService {

    @POST("register")
    Call<KepceResponse<Void>> register(@Body User user);

    @POST("login")
    Call<KepceResponse<Void>> login(@Body User user);

    @POST("logout")
    Call<KepceResponse<Void>> logout();

    @GET("me")
    Call<KepceResponse<User>> getUser();

    @POST("me")
    Call<KepceResponse<User>> editUser(@Body User user);

    @POST("restaurants/{count}/{page}")
    Call<KepceResponse<User>> getRestaurants(@Path("count") int count, @Path("page") int page,
                                             @Query("count") int countAll);

    @GET("restaurant/{id}")
    Call<KepceResponse<User>> getRestaurant(@Path("id") int id);
}
