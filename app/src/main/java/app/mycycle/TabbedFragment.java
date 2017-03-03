package app.mycycle;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by carloconnor on 03/03/17.
 */

public class TabbedFragment extends Fragment {

    private CustomFragmentPagerAdapter customFragmentPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabbed_fragment, container, false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.tabbed_fragment_content);
        mViewPager.setAdapter(customFragmentPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        return view;

    }
}
