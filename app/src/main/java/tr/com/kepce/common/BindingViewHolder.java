package tr.com.kepce.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BindingViewHolder extends RecyclerView.ViewHolder {

    public final ViewDataBinding mBinding;

    public BindingViewHolder(View view) {
        super(view);
        mBinding = DataBindingUtil.bind(view);
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }
}