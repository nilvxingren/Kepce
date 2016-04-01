package tr.com.kepce.order;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.PagedList;
import tr.com.kepce.common.ResponseEvent;

public class OrdersLoadedEvent extends ResponseEvent<PagedList<Order>> {

    public OrdersLoadedEvent(KepceResponse<PagedList<Order>> response) {
        super(response);
    }
}
