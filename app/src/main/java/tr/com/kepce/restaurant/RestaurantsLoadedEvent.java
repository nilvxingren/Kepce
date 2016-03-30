package tr.com.kepce.restaurant;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.PagedList;
import tr.com.kepce.common.ResponseEvent;

public class RestaurantsLoadedEvent extends ResponseEvent<PagedList<Restaurant>> {

    public RestaurantsLoadedEvent(KepceResponse<PagedList<Restaurant>> response) {
        super(response);
    }
}
