package tr.com.kepce.address;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.com.kepce.R;
import tr.com.kepce.common.Kepce;
import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.databinding.ActivityAddressBinding;
import tr.com.kepce.profile.ProfileLoadedEvent;
import tr.com.kepce.profile.ProfileUpdatedEvent;
import tr.com.kepce.profile.User;

public class AddressActivity extends AppCompatActivity {

    private static final String KEY_REQUESTED = "requested";

    private ActivityAddressBinding mBinding;
    private boolean mRequested;
    private View mProgressView;
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddressBinding.inflate(getLayoutInflater());
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContentView = findViewById(R.id.content);
        mProgressView = findViewById(R.id.progress);

        if (savedInstanceState != null) {
            mRequested = savedInstanceState.getBoolean(KEY_REQUESTED);
        }
        if (mRequested) {
            showProgress(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_REQUESTED, mRequested);
    }

    public void onClickSave(View view) {
        mRequested = true;
        showProgress(true);
        Callback<KepceResponse<Void>> callback = new Callback<KepceResponse<Void>>() {
            @Override
            public void onResponse(Call<KepceResponse<Void>> call,
                                   Response<KepceResponse<Void>> response) {
                if (response.code() == 200) {
                    EventBus.getDefault().post(new AddressSavedEvent(response.body()));
                } else {
                    mRequested = false;
                    showProgress(false);
                    Toast.makeText(AddressActivity.this, "Http Error Code: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KepceResponse<Void>> call, Throwable t) {
                mRequested = false;
                showProgress(false);
                Toast.makeText(AddressActivity.this, t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        String id = mBinding.getAddress().getId();
        if (TextUtils.isEmpty(id)) {
            Kepce.getService().addAddress(Kepce.getAuthToken(this),
                    mBinding.getAddress()).enqueue(callback);
        } else {
            Kepce.getService().editAddress(Kepce.getAuthToken(this), id,
                    mBinding.getAddress()).enqueue(callback);
        }
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
        mContentView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

        mBinding.fab.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressSaved(AddressSavedEvent event) {
        if (event.getResponse().code == 0) {
            setResult(RESULT_OK);
            finish();
        } else {
            mRequested = false;
            showProgress(false);
            Toast.makeText(this, "Server Error Code: " + event.getResponse().code,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
