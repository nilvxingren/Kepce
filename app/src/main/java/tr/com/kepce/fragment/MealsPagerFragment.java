package tr.com.kepce.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tr.com.kepce.R;

public class MealsPagerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnMealsPagerFragmentInteractionListener mListener;

    public static MealsPagerFragment newInstance(String param1, String param2) {
        MealsPagerFragment fragment = new MealsPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meals_pager, container, false);

        ViewPager pager = (ViewPager) rootView.findViewById(R.id.meals_pager);
        pager.setAdapter(new MealsFragmentPagerAdapter(getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.meals_tablayout);
        tabLayout.setupWithViewPager(pager);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMealsPagerFragmentInteractionListener) {
            mListener = (OnMealsPagerFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMealsPagerFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
