package tr.com.kepce.meal;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.PagedList;
import tr.com.kepce.common.ResponseEvent;

public class MealsLoadedEvent extends ResponseEvent<PagedList<Meal>> {

    public MealsLoadedEvent(KepceResponse<PagedList<Meal>> response) {
        super(response);
    }
}
