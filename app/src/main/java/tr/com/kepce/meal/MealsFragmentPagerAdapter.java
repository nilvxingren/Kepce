package tr.com.kepce.meal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
        return MealsFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
