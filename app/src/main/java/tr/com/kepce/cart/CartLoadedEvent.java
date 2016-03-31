package tr.com.kepce.cart;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.ResponseEvent;

public class CartLoadedEvent extends ResponseEvent<Cart> {

    public CartLoadedEvent(KepceResponse<Cart> response) {
        super(response);
    }
}
