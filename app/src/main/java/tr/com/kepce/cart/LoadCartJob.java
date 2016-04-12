package tr.com.kepce.cart;

import com.birbit.android.jobqueue.Params;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Response;
import tr.com.kepce.common.BaseJob;
import tr.com.kepce.common.Kepce;
import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.NetworkException;
import tr.com.kepce.common.ServerException;

public class LoadCartJob extends BaseJob {

    private String mAuthToken;

    protected LoadCartJob(int priority, String authToken) {
        super(new Params(priority).requireNetwork().singleInstanceBy("cart-load"));
        mAuthToken = authToken;
    }

    @Override
    public void onRun() throws Throwable {
        Response<KepceResponse<CartResponse>> response = Kepce.getService().getCart(mAuthToken).execute();
        if (response.isSuccessful()) {
            KepceResponse<CartResponse> cartResponse = response.body();
            if (cartResponse.isSuccessful()) {
                EventBus.getDefault().post(new CartLoadedEvent(cartResponse.data));
                return;
            }
            throw new ServerException(cartResponse.code);
        }
        throw new NetworkException(response.code());
    }

    @Override
    protected void onCancel(int cancelReason) {
        EventBus.getDefault().post(new CartLoadedEvent());
    }
}
