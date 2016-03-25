package tr.com.kepce.common;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tr.com.kepce.profile.User;
import tr.com.kepce.restaurant.Restaurant;

public interface KepceService {

    @POST("signup")
    Call<KepceResponse<String>> register(@Body User user);

    @POST("login")
    Call<KepceResponse<String>> login(@Body User user);

    @POST("logout")
    Call<KepceResponse<Void>> logout();

    @GET("me")
    Call<KepceResponse<User>> getUser();

    @POST("me")
    Call<KepceResponse<User>> updateUser(@Body User user);

    @POST("restaurants/{count}/{page}")
    Call<KepceResponse<List<Restaurant>>> getRestaurants(@Path("count") int count,
                                                         @Path("page") int page,
                                                         @Query("count") int countAll);

    @GET("restaurant/{id}")
    Call<KepceResponse<Restaurant>> getRestaurant(@Path("id") int id);
}
