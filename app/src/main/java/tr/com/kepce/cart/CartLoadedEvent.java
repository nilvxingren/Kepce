package tr.com.kepce.cart;

public class CartLoadedEvent {

    private CartResponse mCartResponse;
    private boolean mSuccessful;

    public CartLoadedEvent() {
        this(null);
    }

    public CartLoadedEvent(CartResponse cartResponse) {
        mCartResponse = cartResponse;
        mSuccessful = cartResponse != null;
    }

    public boolean isSuccessful() {
        return mSuccessful;
    }

    public CartResponse getCart() {
        return mCartResponse;
    }
}
