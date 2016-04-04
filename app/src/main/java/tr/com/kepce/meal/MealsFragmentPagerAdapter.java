package tr.com.kepce.meal;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tr.com.kepce.R;

public class MealsFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;

    public MealsFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mTitles = new String[] {
                context.getString(R.string.meals_featured),
                context.getString(R.string.meals_favorites),
                context.getString(R.string.meals_nearby)
        };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return MealsFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
}
