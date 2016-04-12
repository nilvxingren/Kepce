package tr.com.kepce.cart;

import com.birbit.android.jobqueue.Params;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Response;
import tr.com.kepce.common.BaseJob;
import tr.com.kepce.common.Kepce;
import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.NetworkException;
import tr.com.kepce.common.ServerException;

public class ClearCartJob extends BaseJob {

    private String mAuthToken;

    protected ClearCartJob(int priority, String authToken) {
        super(new Params(priority).requireNetwork().singleInstanceBy("cart-clear"));
        mAuthToken = authToken;
    }

    @Override
    public void onRun() throws Throwable {
        Response<KepceResponse<Void>> response = Kepce.getService().clearCart(mAuthToken).execute();
        if (response.isSuccessful()) {
            KepceResponse<Void> cartResponse = response.body();
            if (cartResponse.isSuccessful()) {
                EventBus.getDefault().post(new CartClearedEvent(true));
                return;
            }
            throw new ServerException(cartResponse.code);
        }
        throw new NetworkException(response.code());
    }

    @Override
    protected void onCancel(int cancelReason) {
        EventBus.getDefault().post(new CartClearedEvent(false));
    }
}
