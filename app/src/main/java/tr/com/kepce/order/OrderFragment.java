package tr.com.kepce.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tr.com.kepce.databinding.FragmentOrderBinding;

public class OrderFragment extends Fragment {

    private static final String KEY_ORDER = "order";

    public static OrderFragment newInstance(Order order) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ORDER, order);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentOrderBinding binding = FragmentOrderBinding.inflate(inflater, container, false);
        binding.setOrder((Order) getArguments().getParcelable(KEY_ORDER));
        return binding.getRoot();
    }
}
