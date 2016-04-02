package tr.com.kepce.order;

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
import tr.com.kepce.common.BindingViewHolder;
import tr.com.kepce.order.OrdersFragment.OnOrdersFragmentInteractionListener;
import tr.com.kepce.restaurant.Restaurant;

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    private final List<Order> mValues;
    private final OnOrdersFragmentInteractionListener mListener;

    public OrdersRecyclerViewAdapter(OnOrdersFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void addItems(Collection<Order> orders) {
        mValues.addAll(orders);
    }

    public void clearItems() {
        mValues.clear();
    }
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_orders_item, parent, false);
        return new BindingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        final Order order = mValues.get(position);
        holder.getBinding().setVariable(BR.order, order);
        holder.getBinding().executePendingBindings();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onOrderSelected(order);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
