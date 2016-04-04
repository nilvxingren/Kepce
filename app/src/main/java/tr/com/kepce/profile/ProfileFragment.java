package tr.com.kepce.profile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import tr.com.kepce.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private static final String KEY_REQUESTED = "requested";

    private FragmentProfileBinding mBinding;
    private OnProfileFragmentInteractionListener mListener;
    private boolean mRequested;
    private View mProgressView;
    private View mContentView;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mRequested = savedInstanceState.getBoolean(KEY_REQUESTED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(inflater);

        mContentView = mBinding.getRoot().findViewById(R.id.content);
        mProgressView = mBinding.getRoot().findViewById(R.id.progress);

        if (!mRequested) {
            showProgress(true);
            loadData();
        } else if (EventBus.getDefault().getStickyEvent(ProfileLoadedEvent.class) == null) {
            showProgress(true);
        }

        return mBinding.getRoot();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileFragmentInteractionListener) {
            mListener = (OnProfileFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProfileFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_REQUESTED, mRequested);
    }

    public void onClickUpdate(View view) {
        view.setEnabled(false);

        User user = mBinding.getUser();
        user.setFirstName(mBinding.firstname.getText().toString());
        user.setLastName(mBinding.lastname.getText().toString());
        try {
            user.setGender(Gender.valueOf(mBinding.gender.getText().toString()));
        } catch (IllegalArgumentException ex) {
        }
        user.setBirthday(mBinding.birthday.getText().toString());
        user.setPhoneNumber(mBinding.phonenumber.getText().toString());
        try {
            user.setHeight(Integer.parseInt(mBinding.height.getText().toString()));
        } catch (NumberFormatException ex) {
        }
        try {
            user.setWeight(Integer.parseInt(mBinding.weight.getText().toString()));
        } catch (NumberFormatException ex) {
        }

        Kepce.getService().updateUser(Kepce.peekAuthToken(getContext()), mBinding.getUser())
                .enqueue(new Callback<KepceResponse<Void>>() {
                    @Override
                    public void onResponse(Call<KepceResponse<Void>> call,
                                           Response<KepceResponse<Void>> response) {
                        if (response.code() == 200) {
                            EventBus.getDefault().post(new ProfileUpdatedEvent(response.body()));
                        } else {
                            Toast.makeText(getContext(), "Http Error Code: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<KepceResponse<Void>> call, Throwable t) {
                        mBinding.update.setEnabled(true);
                        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
    }

    private void loadData() {
        if (mRequested) {
            return;
        }
        mRequested = true;
        Kepce.getService().getUser(Kepce.peekAuthToken(getContext()))
                .enqueue(new Callback<KepceResponse<User>>() {
                    @Override
                    public void onResponse(Call<KepceResponse<User>> call,
                                           Response<KepceResponse<User>> response) {
                        if (response.code() == 200) {
                            EventBus.getDefault().postSticky(new ProfileLoadedEvent(response.body()));
                        } else {
                            Toast.makeText(getContext(), "Http Error Code: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<KepceResponse<User>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                        showProgress(false);
                    }
                });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onProfileLoaded(ProfileLoadedEvent event) {
        if (event.getResponse().code == 0) {
            mBinding.setUser(event.getResponse().data);
        } else {
            Toast.makeText(getContext(), "Error Code: " + event.getResponse().code,
                    Toast.LENGTH_SHORT).show();
        }
        showProgress(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProfileUpdated(ProfileUpdatedEvent event) {
        if (event.getResponse().code == 0) {
            mBinding.update.setEnabled(true);
        } else {
            Toast.makeText(getContext(), "Server Error Code: " + event.getResponse().code,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnProfileFragmentInteractionListener {
    }
}
