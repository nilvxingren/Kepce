package tr.com.kepce.common;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tr.com.kepce.address.Address;
import tr.com.kepce.cart.Cart;
import tr.com.kepce.meal.Favorite;
import tr.com.kepce.meal.Meal;
import tr.com.kepce.order.Order;
import tr.com.kepce.profile.User;
import tr.com.kepce.restaurant.Restaurant;

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
    Call<KepceResponse<Void>> updateUser(@Header("Authorization") String authorization,
                                         @Body User user);

    @POST("restaurants/{count}/{page}")
    Call<KepceResponse<PagedList<Restaurant>>> listRestaurants(@Header("Authorization") String authorization,
                                                               @Path("count") int count,
                                                               @Path("page") int page,
                                                               @Query("count") int countAll);

    @POST("meals/{count}/{page}")
    Call<KepceResponse<PagedList<Meal>>> listMeals(@Header("Authorization") String authorization,
                                                   @Path("count") int count,
                                                   @Path("page") int page,
                                                   @Query("count") int countAll);

    @POST("favorites/{count}/{page}")
    Call<KepceResponse<PagedList<Favorite>>> listFavorites(@Header("Authorization") String authorization,
                                                           @Path("count") int count,
                                                           @Path("page") int page,
                                                           @Query("count") int countAll);

    @GET("favorite/{id}")
    Call<KepceResponse<Favorite>> getFavorite(@Header("Authorization") String authorization,
                                              @Path("id") String id);

    @FormUrlEncoded
    @POST("favorite")
    Call<KepceResponse<Void>> addFavorite(@Header("Authorization") String authorization,
                                          @Field("mealId") String mealId);

    @FormUrlEncoded
    @POST("favorites/delete/meal/{id}")
    Call<KepceResponse<Void>> removeFavorite(@Header("Authorization") String authorization,
                                             @Field("id") String mealId);

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

    @GET("cart")
    Call<KepceResponse<Cart>> getCart(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("cart")
    Call<KepceResponse<Void>> addToCart(@Header("Authorization") String authorization,
                                        @Field("mealId") String mealId,
                                        @Field("quantity") Integer quantity);

    @FormUrlEncoded
    @POST("cart/remove")
    Call<KepceResponse<Void>> removeFromCart(@Header("Authorization") String authorization,
                                             @Field("orderMealId") String mealId,
                                             @Field("quantity") Integer quantity);

    @POST("cart/empty")
    Call<KepceResponse<Void>> clearCart(@Header("Authorization") String authorization);

    @POST("orders/{count}/{page}")
    Call<KepceResponse<PagedList<Order>>> listOrders(@Header("Authorization") String authorization,
                                                     @Path("count") int count,
                                                     @Path("page") int page,
                                                     @Query("count") int countAll);
}
