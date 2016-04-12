package tr.com.kepce.cart;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import tr.com.kepce.R;
import tr.com.kepce.common.BaseFragment;
import tr.com.kepce.common.Jobs;
import tr.com.kepce.common.Kepce;
import tr.com.kepce.view.EmptyRecyclerView;

public class CartFragment extends BaseFragment {

    private CartRecyclerViewAdapter mAdapter;
    private OnCartFragmentInteractionListener mListener;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        setContentView(view.findViewById(R.id.content));
        setProgressView(view.findViewById(R.id.progress));

        EmptyRecyclerView recyclerView = (EmptyRecyclerView) getContentView().findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CartRecyclerViewAdapter(mListener);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setEmptyView(getContentView().findViewById(android.R.id.empty));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        CartController.getInstance().load(true, Kepce.peekAuthToken(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCartFragmentInteractionListener) {
            mListener = (OnCartFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCartFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCartLoaded(CartLoadedEvent event) {
        if (event.isSuccessful()) {
            mAdapter.clearItems();
            mAdapter.addItems(event.getCart().getEntities());
            mAdapter.notifyDataSetChanged();
        }
        showProgress(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCartCleared(CartClearedEvent event) {
        if (event.isSuccessful()) {
            mAdapter.clearItems();
            mAdapter.notifyDataSetChanged();
        }
        showProgress(false);
    }

    public interface OnCartFragmentInteractionListener {
    }
}
