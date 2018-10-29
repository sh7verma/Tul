package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.CalendarFragment;
import fragments.StatisticsFragment;

public class DashboardPagerAdapter extends FragmentPagerAdapter {

    public DashboardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return 2;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CalendarFragment();
            case 1:
                return new StatisticsFragment();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        String title[] = {"CALENDAR","STATISTICS"};
        return title[position];
    }
}