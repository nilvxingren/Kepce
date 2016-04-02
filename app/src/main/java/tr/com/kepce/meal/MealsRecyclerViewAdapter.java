package tr.com.kepce.meal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import tr.com.kepce.BR;
import tr.com.kepce.R;
import tr.com.kepce.common.BindingViewHolder;
import tr.com.kepce.meal.MealsFragment.OnMealsFragmentInteractionListener;

public class MealsRecyclerViewAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    private final List<Meal> mValues;
    private final OnMealsFragmentInteractionListener mListener;

    public MealsRecyclerViewAdapter(OnMealsFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void addItems(Collection<Meal> meals) {
        mValues.addAll(meals);
    }

    public void clearItems() {
        mValues.clear();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meals_item, parent, false);
        return new BindingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        final Meal meal = mValues.get(position);
        holder.getBinding().setVariable(BR.meal, meal);
        holder.getBinding().executePendingBindings();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMealSelected(meal);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
