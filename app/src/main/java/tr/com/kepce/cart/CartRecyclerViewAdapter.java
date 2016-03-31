package tr.com.kepce.cart;

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
import tr.com.kepce.address.Address;
import tr.com.kepce.common.BindingViewHolder;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    private final List<CartEntity> mValues;
    private final CartFragment.OnCartFragmentInteractionListener mListener;

    public CartRecyclerViewAdapter(CartFragment.OnCartFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void addItem(CartEntity cartEntity) {
        mValues.add(cartEntity);
    }

    public void addItems(CartEntity... cartEntities) {
        mValues.addAll(Arrays.asList(cartEntities));
    }

    public void addItems(Collection<CartEntity> restaurants) {
        mValues.addAll(restaurants);
    }

    public void clearItems() {
        mValues.clear();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cart_item, parent, false);
        return new BindingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        final CartEntity entity = mValues.get(position);
        holder.getBinding().setVariable(BR.cartEntity, entity);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onCartEntitySelected(entity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
