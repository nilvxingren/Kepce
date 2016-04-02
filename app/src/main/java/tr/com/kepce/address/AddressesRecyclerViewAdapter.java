package tr.com.kepce.address;

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
import tr.com.kepce.address.AddressesFragment.OnAddressesFragmentInteractionListener;
import tr.com.kepce.common.BindingViewHolder;

public class AddressesRecyclerViewAdapter
        extends RecyclerView.Adapter<BindingViewHolder> {

    private final List<Address> mValues;
    private final OnAddressesFragmentInteractionListener mListener;

    public AddressesRecyclerViewAdapter(OnAddressesFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void addItems(Collection<Address> restaurants) {
        mValues.addAll(restaurants);
    }

    public void clearItems() {
        mValues.clear();
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_addresses_item, parent, false);
        return new BindingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        final Address address = mValues.get(position);
        holder.getBinding().setVariable(BR.address, address);
        holder.getBinding().executePendingBindings();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onAddressSelected(address);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
