package tr.com.kepce.cart;

import tr.com.kepce.common.Jobs;

public class CartController {

    private static CartController INSTANCE = new CartController();

    private CartController() {
    }

    public static CartController getInstance() {
        return INSTANCE;
    }

    public void load(boolean fromUi, String authToken) {
        Jobs.getManager().addJobInBackground(
                new LoadCartJob(fromUi ? Jobs.PRIORITY_UX : Jobs.PRIORITY_MIN, authToken));
    }

    public void clear(boolean fromUi, String authToken) {
        Jobs.getManager().addJobInBackground(
                new ClearCartJob(fromUi ? Jobs.PRIORITY_UX : Jobs.PRIORITY_MIN, authToken));
    }
}
