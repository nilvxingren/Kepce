package tr.com.kepce.cart;

public class CartClearedEvent {

    private boolean mSuccessful;

    public CartClearedEvent(boolean successful) {
        mSuccessful = successful;
    }

    public boolean isSuccessful() {
        return mSuccessful;
    }
}
