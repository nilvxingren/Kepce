package tr.com.kepce.common;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tr.com.kepce.address.Address;
import tr.com.kepce.meal.Favorite;
import tr.com.kepce.meal.Meal;
import tr.com.kepce.profile.User;
import tr.com.kepce.restaurant.Restaurant;

public interface KepceService {

    @POST("restaurants/{count}/{page}")
    Call<KepceResponse<PagedList<Restaurant>>> listRestaurants(@Path("count") int count,
                                                               @Path("page") int page,
                                                               @Query("count") int countAll);

    @GET("restaurant/{id}")
    Call<KepceResponse<Restaurant>> getRestaurant(@Path("id") String id);

    @POST("meals/{count}/{page}")
    Call<KepceResponse<PagedList<Meal>>> listMeals(@Path("count") int count,
                                                   @Path("page") int page,
                                                   @Query("count") int countAll);

    @GET("meal/{id}")
    Call<KepceResponse<Meal>> getMeal(@Path("id") String id);

    @POST("signup")
    Call<KepceResponse<User>> register(@Body User user);

    @POST("login")
    Call<KepceResponse<String>> login(@Body User user);

    @POST("logout")
    Call<KepceResponse<Void>> logout(@Header("Authorization") String authorization);

    @GET("me")
    Call<KepceResponse<User>> getUser(@Header("Authorization") String authorization);

    @POST("me")
    Call<KepceResponse<Void>> updateUser(@Header("Authorization") String authorization,
                                         @Body User user);

    @POST("favorites/{count}/{page}")
    Call<KepceResponse<PagedList<Favorite>>> listFavorites(@Header("Authorization") String authorization,
                                                           @Path("count") int count,
                                                           @Path("page") int page,
                                                           @Query("count") int countAll);

    @GET("favorite/{id}")
    Call<KepceResponse<Favorite>> getFavorite(@Header("Authorization") String authorization,
                                              @Path("id") String id);

    // param = mealId
    @POST("favorite")
    Call<KepceResponse<Void>> addFavorite(@Header("Authorization") String authorization);

    @GET("me/addresses")
    Call<KepceResponse<List<Address>>> listAddresses(@Header("Authorization") String authorization);

    @POST("me/address")
    Call<KepceResponse<Void>> addAddress(@Header("Authorization") String authorization,
                                         @Body Address address);

    @POST("me/address/edit/{id}")
    Call<KepceResponse<Void>> editAddress(@Header("Authorization") String authorization,
                                          @Path("id") String id,
                                          @Body Address address);

    @POST("me/address/delete/{id}")
    Call<KepceResponse<Void>> deleteAddress(@Header("Authorization") String authorization,
                                            @Path("id") String id);
}
