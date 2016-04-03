package tr.com.kepce.meal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.com.kepce.R;
import tr.com.kepce.common.Kepce;
import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.databinding.ActivityMealBinding;

public class MealActivity extends AppCompatActivity {

    public static final String KEY_MEAL = "meal";

    private ActivityMealBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_meal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Meal meal = getIntent().getParcelableExtra(KEY_MEAL);
            mBinding.setMeal(meal);
            mBinding.setQuantity(1);
        }
    }

    public void onClickAdd(View view) {
        view.setEnabled(false);
        Kepce.getService().addToCart(Kepce.peekAuthToken(this), mBinding.getMeal().getId(),
                mBinding.getQuantity()).enqueue(new Callback<KepceResponse<Void>>() {
            @Override
            public void onResponse(Call<KepceResponse<Void>> call,
                                   Response<KepceResponse<Void>> response) {
                finish();
            }

            @Override
            public void onFailure(Call<KepceResponse<Void>> call, Throwable t) {
            }
        });
    }
}
