package tr.com.kepce.restaurant;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import tr.com.kepce.BR;
import tr.com.kepce.R;
import tr.com.kepce.order.Order;

public class RestaurantsRecyclerViewAdapter
        extends RecyclerView.Adapter<RestaurantsRecyclerViewAdapter.ViewHolder> {

    private final List<Restaurant> mValues;
    private final RestaurantsFragment.OnRestaurantsFragmentInteractionListener mListener;

    public RestaurantsRecyclerViewAdapter(RestaurantsFragment.OnRestaurantsFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void addItem(Restaurant restaurant) {
        mValues.add(restaurant);
    }

    public void addItems(Restaurant... restaurants) {
        mValues.addAll(Arrays.asList(restaurants));
    }

    public void addItems(Collection<Restaurant> restaurants) {
        mValues.addAll(restaurants);
    }

    public void clearItems() {
        mValues.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_restaurants_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Restaurant restaurant = mValues.get(position);
        holder.getBinding().setVariable(BR.restaurant, restaurant);
        holder.getBinding().executePendingBindings();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRestaurantSelected(restaurant);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ViewDataBinding mBinding;

        public ViewHolder(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}
