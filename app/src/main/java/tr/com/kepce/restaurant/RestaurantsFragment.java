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

public class RestaurantsFragment extends Fragment {

    private RestaurantsRecyclerViewAdapter mAdapter;
    private OnRestaurantsFragmentInteractionListener mListener;

    public static RestaurantsFragment newInstance() {
        return new RestaurantsFragment();
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

        Kepce.getService().listRestaurants(20, 0, 0)
                .enqueue(new Callback<KepceResponse<PagedList<Restaurant>>>() {
                    @Override
                    public void onResponse(Call<KepceResponse<PagedList<Restaurant>>> call,
                                           Response<KepceResponse<PagedList<Restaurant>>> response) {
                        if (response.body().code == 0) {
                            EventBus.getDefault().postSticky(new RestaurantsLoadedEvent(response.body().data));
                        } else {
                            Toast.makeText(getContext(), "Error Code: " + response.body().code,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<KepceResponse<PagedList<Restaurant>>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

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
        mAdapter.addItems(event.getRestaurants().list);
        mAdapter.notifyDataSetChanged();
    }

    public interface OnRestaurantsFragmentInteractionListener {

        void onRestaurantSelected(Restaurant restaurant);
    }
}
