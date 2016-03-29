package tr.com.kepce.meal;

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
import tr.com.kepce.restaurant.RestaurantsLoadedEvent;

public class MealsFragment extends Fragment {

    private static final String KEY_TYPE = "type";

    private MealsRecyclerViewAdapter mAdapter;
    private OnMealsFragmentInteractionListener mListener;

    public static MealsFragment newInstance(int type) {
        MealsFragment fragment = new MealsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MealsRecyclerViewAdapter(mListener);
        recyclerView.setAdapter(mAdapter);

        Kepce.getService().listMeals(20, 0, 0).enqueue(new Callback<KepceResponse<PagedList<Meal>>>() {
            @Override
            public void onResponse(Call<KepceResponse<PagedList<Meal>>> call,
                                   Response<KepceResponse<PagedList<Meal>>> response) {
                if (response.body().code == 0) {
                    EventBus.getDefault().postSticky(new MealsLoadedEvent(response.body().data));
                } else {
                    Toast.makeText(getContext(), "Error Code: " + response.body().code,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KepceResponse<PagedList<Meal>>> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMealsLoaded(MealsLoadedEvent event) {
        mAdapter.clearItems();
        mAdapter.addItems(event.getMeals().list);
        mAdapter.notifyDataSetChanged();
    }

    public interface OnMealsFragmentInteractionListener {

        void onMealSelected(Meal meal);
    }
}
