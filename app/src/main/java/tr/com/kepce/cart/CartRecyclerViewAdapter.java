package tr.com.kepce.cart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tr.com.kepce.R;
import tr.com.kepce.address.Address;

public class CartRecyclerViewAdapter
        extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).);
        //holder.mContentView.setText(mValues.get(position).);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onCartEntitySelected(holder.mItem);
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
        public CartEntity mItem;

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
