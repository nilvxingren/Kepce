package tr.com.kepce.address;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.com.kepce.R;
import tr.com.kepce.common.Kepce;
import tr.com.kepce.common.KepceResponse;

public class AddressesFragment extends Fragment {

    private static final String KEY_REQUESTED = "requested";

    private AddressesRecyclerViewAdapter mAdapter;
    private OnAddressesFragmentInteractionListener mListener;
    private boolean mRequested;
    private View mProgressView;
    private View mContentView;

    public static AddressesFragment newInstance() {
        return new AddressesFragment();
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
        View view = inflater.inflate(R.layout.fragment_addresses, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AddressesRecyclerViewAdapter(mListener);
        recyclerView.setAdapter(mAdapter);

        mContentView = recyclerView;
        mProgressView = view.findViewById(R.id.progress);

        if (!mRequested) {
            showProgress(true);
            loadData();
        } else if (EventBus.getDefault().getStickyEvent(AddressesLoadedEvent.class) == null) {
            showProgress(true);
        }

        return view;
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
        if (context instanceof OnAddressesFragmentInteractionListener) {
            mListener = (OnAddressesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddressesFragmentInteractionListener");
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
        Kepce.getService().listAddresses(Kepce.getAuthToken(getContext()))
                .enqueue(new Callback<KepceResponse<List<Address>>>() {
                    @Override
                    public void onResponse(Call<KepceResponse<List<Address>>> call,
                                           Response<KepceResponse<List<Address>>> response) {
                        if (response.code() == 200) {
                            EventBus.getDefault().postSticky(new AddressesLoadedEvent(response.body()));
                        } else {
                            Toast.makeText(getContext(), "Http Error Code: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<KepceResponse<List<Address>>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                        showProgress(false);
                    }
                });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onAddressesLoaded(AddressesLoadedEvent event) {
        if (event.getResponse().code == 0) {
            mAdapter.clearItems();
            mAdapter.addItems(event.getResponse().data);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Server Error Code: " + event.getResponse().code,
                    Toast.LENGTH_SHORT).show();
        }
        showProgress(false);
    }

    public interface OnAddressesFragmentInteractionListener {

        void onAddressSelected(Address address);
    }
}
