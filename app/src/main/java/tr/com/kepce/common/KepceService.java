package tr.com.kepce.common;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tr.com.kepce.profile.User;
import tr.com.kepce.restaurant.Restaurant;
import tr.com.kepce.restaurant.Restaurants;

public interface KepceService {

    @POST("signup")
    Call<KepceResponse<User>> register(@Body User user);

    @POST("login")
    Call<KepceResponse<String>> login(@Body User user);

    @POST("logout")
    Call<KepceResponse<Void>> logout(@Header("Authorization") String authorization);

    @GET("me")
    Call<KepceResponse<User>> getUser(@Header("Authorization") String authorization);

    @POST("me")
    Call<KepceResponse<User>> updateUser(@Header("Authorization") String authorization,
                                         @Body User user);

    @POST("restaurants/{count}/{page}")
    Call<KepceResponse<Restaurants>> getRestaurants(@Header("Authorization") String authorization,
                                                    @Path("count") int count,
                                                    @Path("page") int page,
                                                    @Query("count") int countAll);

    @GET("restaurant/{id}")
    Call<KepceResponse<Restaurant>> getRestaurant(@Header("Authorization") String authorization,
                                                  @Path("id") int id);
}
