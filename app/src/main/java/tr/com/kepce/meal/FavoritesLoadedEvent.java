package tr.com.kepce.meal;

import java.util.List;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.PagedList;
import tr.com.kepce.common.ResponseEvent;

public class FavoritesLoadedEvent extends ResponseEvent<List<Favorite>> {

    public FavoritesLoadedEvent(KepceResponse<List<Favorite>> response) {
        super(response);
    }
}
