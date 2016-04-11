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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.com.kepce.R;
import tr.com.kepce.common.Kepce;
import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.PagedList;
import tr.com.kepce.view.EmptyRecyclerView;

public class MealsFragment extends Fragment {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FAVORITE = 1;
    public static final int TYPE_NEARBY = 2;

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

        mContentView = view.findViewById(R.id.content);
        mProgressView = view.findViewById(R.id.progress);

        EmptyRecyclerView recyclerView = (EmptyRecyclerView) mContentView.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MealsRecyclerViewAdapter(mListener);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setEmptyView(mContentView.findViewById(android.R.id.empty));

        if (!mRequested) {
            showProgress(true);
            loadData();
        } else if (EventBus.getDefault().getStickyEvent(MealsLoadedEvent.class) == null
                && EventBus.getDefault().getStickyEvent(FavoritesLoadedEvent.class) == null) {
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
        switch (getArguments().getInt(KEY_TYPE, TYPE_NORMAL)) {
            case TYPE_FAVORITE: {
                Kepce.getService().listFavorites(Kepce.peekAuthToken(getContext()))
                        .enqueue(new Callback<KepceResponse<List<Favorite>>>() {
                            @Override
                            public void onResponse(Call<KepceResponse<List<Favorite>>> call,
                                                   Response<KepceResponse<List<Favorite>>> response) {
                                if (response.code() == 200) {
                                    EventBus.getDefault().postSticky(new FavoritesLoadedEvent(response.body()));
                                } else {
                                    Toast.makeText(getContext(), "Http Error Code: " + response.code(),
                                            Toast.LENGTH_SHORT).show();
                                    showProgress(false);
                                }
                            }

                            @Override
                            public void onFailure(Call<KepceResponse<List<Favorite>>> call, Throwable t) {
                                Toast.makeText(getContext(), t.getLocalizedMessage(),
                                        Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });
            }
            break;
            case TYPE_NEARBY: {
                Kepce.getService().filterMeals(Kepce.peekAuthToken(getContext()), 20, 0, 0, 0, 0, Float.MAX_VALUE)
                        .enqueue(new Callback<KepceResponse<PagedList<Meal>>>() {
                            @Override
                            public void onResponse(Call<KepceResponse<PagedList<Meal>>> call,
                                                   Response<KepceResponse<PagedList<Meal>>> response) {
                                if (response.code() == 200) {
                                    EventBus.getDefault().postSticky(new NearbyLoadedEvent(response.body()));
                                } else {
                                    Toast.makeText(getContext(), "Http Error Code: " + response.code(),
                                            Toast.LENGTH_SHORT).show();
                                    showProgress(false);
                                }
                            }

                            @Override
                            public void onFailure(Call<KepceResponse<PagedList<Meal>>> call, Throwable t) {
                                Toast.makeText(getContext(), t.getLocalizedMessage(),
                                        Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });
            }
            break;
            default: {
                Kepce.getService().listMeals(Kepce.peekAuthToken(getContext()), 20, 0, 0)
                        .enqueue(new Callback<KepceResponse<PagedList<Meal>>>() {
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
                                Toast.makeText(getContext(), t.getLocalizedMessage(),
                                        Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });
            }
            break;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onFavoritesLoaded(FavoritesLoadedEvent event) {
        if (getArguments().getInt(KEY_TYPE, TYPE_NORMAL) != TYPE_FAVORITE) {
            return;
        }
        if (event.getResponse().code == 0) {
            mAdapter.clearItems();
            List<Meal> meals = new ArrayList<>(event.getResponse().data.size());
            for (Favorite favorite : event.getResponse().data) {
                meals.add(favorite.getMeal());
            }
            mAdapter.addItems(meals);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Server Error Code: " + event.getResponse().code,
                    Toast.LENGTH_SHORT).show();
        }
        showProgress(false);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMealsLoaded(MealsLoadedEvent event) {
        if (getArguments().getInt(KEY_TYPE, TYPE_NORMAL) != TYPE_NORMAL) {
            return;
        }
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onNearbyLoaded(NearbyLoadedEvent event) {
        if (getArguments().getInt(KEY_TYPE, TYPE_NORMAL) != TYPE_NEARBY) {
            return;
        }
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

        void onCartSelected();
    }
}
