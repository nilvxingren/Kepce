package tr.com.kepce.restaurant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class RestaurantsFragment extends Fragment {

    private RestaurantsRecyclerViewAdapter mAdapter;
    private OnRestaurantsFragmentInteractionListener mListener;

    public static RestaurantsFragment newInstance() {
        return new RestaurantsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Kepce.getService().getRestaurants(Kepce.getAuthToken(getContext(), true), 20, 0, 0)
                .enqueue(new Callback<KepceResponse<Restaurants>>() {
                    @Override
                    public void onResponse(Call<KepceResponse<Restaurants>> call,
                                           Response<KepceResponse<Restaurants>> response) {
                        EventBus.getDefault().postSticky(new RestaurantsLoadedEvent(response.body().data));
                    }

                    @Override
                    public void onFailure(Call<KepceResponse<Restaurants>> call, Throwable t) {
                    }
                });
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);

        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RestaurantsRecyclerViewAdapter(mListener);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRestaurantsFragmentInteractionListener) {
            mListener = (OnRestaurantsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRestaurantsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRestaurantsLoaded(RestaurantsLoadedEvent event) {
        mAdapter.clearItems();
        mAdapter.addItems(event.getRestaurants().response);
    }

    public interface OnRestaurantsFragmentInteractionListener {

        void onRestaurantSelected(Restaurant restaurant);
    }
}
