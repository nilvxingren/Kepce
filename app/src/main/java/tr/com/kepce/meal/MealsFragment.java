package tr.com.kepce.meal;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.com.kepce.R;
import tr.com.kepce.common.Kepce;
import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.PagedList;

public class MealsFragment extends Fragment {

    private static final String KEY_TYPE = "type";
    private static final String KEY_REQUESTED = "requested";

    private MealsRecyclerViewAdapter mAdapter;
    private OnMealsFragmentInteractionListener mListener;
    private boolean mRequested;
    private View mProgressView;
    private View mContentView;

    public static MealsFragment newInstance(int type) {
        MealsFragment fragment = new MealsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MealsRecyclerViewAdapter(mListener);
        recyclerView.setAdapter(mAdapter);

        mContentView = recyclerView;
        mProgressView = view.findViewById(R.id.progress);

        if (!mRequested) {
            showProgress(true);
            loadData();
        } else if (EventBus.getDefault().getStickyEvent(MealsLoadedEvent.class) == null) {
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
        if (context instanceof OnMealsFragmentInteractionListener) {
            mListener = (OnMealsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMealsFragmentInteractionListener");
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
        Kepce.getService().listMeals(20, 0, 0).enqueue(new Callback<KepceResponse<PagedList<Meal>>>() {
            @Override
            public void onResponse(Call<KepceResponse<PagedList<Meal>>> call,
                                   Response<KepceResponse<PagedList<Meal>>> response) {
                if (response.code() == 200) {
                    EventBus.getDefault().postSticky(new MealsLoadedEvent(response.body()));
                } else {
                    Toast.makeText(getContext(), "Http Error Code: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    showProgress(false);
                }
            }

            @Override
            public void onFailure(Call<KepceResponse<PagedList<Meal>>> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMealsLoaded(MealsLoadedEvent event) {
        if (event.getResponse().code == 0) {
            mAdapter.clearItems();
            mAdapter.addItems(event.getResponse().data.list);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Server Error Code: " + event.getResponse().code,
                    Toast.LENGTH_SHORT).show();
        }
        showProgress(false);
    }

    public interface OnMealsFragmentInteractionListener {

        void onMealSelected(Meal meal);
    }
}
