package tr.com.kepce.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by eray on 15/03/16.
 */
public class MealsFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static final String[] TITLES = {"Featured", "Favorites", "Nearby"};

    public MealsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        return new MealsFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
