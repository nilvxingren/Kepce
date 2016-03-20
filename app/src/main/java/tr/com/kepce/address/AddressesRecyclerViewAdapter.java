package tr.com.kepce.address;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tr.com.kepce.R;
import tr.com.kepce.address.AddressesFragment.OnAddressesFragmentInteractionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddressesRecyclerViewAdapter
        extends RecyclerView.Adapter<AddressesRecyclerViewAdapter.ViewHolder> {

    private final List<Address> mValues;
    private final OnAddressesFragmentInteractionListener mListener;

    public AddressesRecyclerViewAdapter(OnAddressesFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void addItem(Address address) {
        mValues.add(address);
    }

    public void addItems(Address... addresses) {
        mValues.addAll(Arrays.asList(addresses));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_addresses_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onAddressSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mIdView;
        public final TextView mContentView;
        public Address mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
