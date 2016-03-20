package tr.com.kepce.meal;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tr.com.kepce.R;

public class MealsPagerFragment extends Fragment {

    public static MealsPagerFragment newInstance() {
        return new MealsPagerFragment();
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
}
