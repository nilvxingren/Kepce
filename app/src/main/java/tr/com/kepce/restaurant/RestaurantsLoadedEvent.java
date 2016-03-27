package tr.com.kepce.restaurant;

public class RestaurantsLoadedEvent {

    private Restaurants mRestaurants;

    public RestaurantsLoadedEvent(Restaurants restaurants) {
        mRestaurants = restaurants;
    }

    public Restaurants getRestaurants() {
        return mRestaurants;
    }
}
