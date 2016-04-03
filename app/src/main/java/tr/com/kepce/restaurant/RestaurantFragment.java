package tr.com.kepce.restaurant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tr.com.kepce.R;
import tr.com.kepce.databinding.FragmentOrderBinding;
import tr.com.kepce.databinding.FragmentRestaurantBinding;
import tr.com.kepce.meal.Meal;
import tr.com.kepce.order.Order;

public class RestaurantFragment extends Fragment {

    private static final String KEY_RESTAURANT  = "restaurant";

    private OnRestaurantFragmentInteractionListener mListener;

    public static RestaurantFragment newInstance(Restaurant restaurant) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRestaurantBinding binding = FragmentRestaurantBinding.inflate(inflater,
                container, false);
        binding.setRestaurant((Restaurant) getArguments().getParcelable(KEY_RESTAURANT));
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRestaurantFragmentInteractionListener) {
            mListener = (OnRestaurantFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRestaurantFragmentInteractionListener {

        void onMealSelected(Meal meal);
    }
}
