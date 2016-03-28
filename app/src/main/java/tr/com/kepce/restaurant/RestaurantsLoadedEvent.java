package tr.com.kepce.restaurant;

import tr.com.kepce.common.PagedList;

public class RestaurantsLoadedEvent {

    private PagedList<Restaurant> mRestaurants;

    public RestaurantsLoadedEvent(PagedList<Restaurant> restaurants) {
        mRestaurants = restaurants;
    }

    public PagedList<Restaurant> getRestaurants() {
        return mRestaurants;
    }
}
