package tr.com.kepce.meal;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import tr.com.kepce.R;
import tr.com.kepce.cart.CartEntity;
import tr.com.kepce.meal.MealsFragment.OnMealsFragmentInteractionListener;
import tr.com.kepce.restaurant.Restaurant;

public class MealsRecyclerViewAdapter extends RecyclerView.Adapter<MealsRecyclerViewAdapter.ViewHolder> {

    private final List<Meal> mValues;
    private final OnMealsFragmentInteractionListener mListener;

    public MealsRecyclerViewAdapter(OnMealsFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void addItem(Meal meal) {
        mValues.add(meal);
    }

    public void addItems(Meal... meals) {
        mValues.addAll(Arrays.asList(meals));
    }

    public void addItems(Collection<Meal> meals) {
        mValues.addAll(meals);
    }

    public void clearItems() {
        mValues.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meals_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mRestaurantView.setText(mValues.get(position).getRestaurant().getName());
        holder.mCaloriesView.setText(mValues.get(position).getCalories().toString());
        holder.mCo2eView.setText(mValues.get(position).getCo2e().toString());
        if (mValues.get(position).getPhoto() != null) {
            holder.mPhotoView.setImageURI(Uri.parse(mValues.get(position).getPhoto()));
        } else {
            holder.mPhotoView.setImageResource(R.drawable.tost);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onMealSelected(holder.mItem);
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
        public final TextView mRestaurantView;
        public final TextView mCaloriesView;
        public final TextView mCo2eView;
        public final SimpleDraweeView mPhotoView;
        public Meal mItem;

        public ViewHolder(View view) {
            super(view);
            mNameView = (TextView) view.findViewById(R.id.name);
            mRestaurantView = (TextView) view.findViewById(R.id.restaurant_name);
            mCaloriesView = (TextView) view.findViewById(R.id.calories);
            mCo2eView = (TextView) view.findViewById(R.id.carbon_footprint);
            mPhotoView = (SimpleDraweeView) view.findViewById(R.id.image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
