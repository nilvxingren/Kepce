package tr.com.kepce.restaurant;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mDistanceView.setText(mValues.get(position).getCo2e().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRestaurantSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mNameView;
        public final TextView mDistanceView;
        public Restaurant mItem;

        public ViewHolder(View view) {
            super(view);
            mNameView = (TextView) view.findViewById(R.id.name);
            mDistanceView = (TextView) view.findViewById(R.id.distance);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
