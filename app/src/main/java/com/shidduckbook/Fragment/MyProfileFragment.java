package com.shidduckbook.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shidduckbook.Fragment.EducationalFragment;
import com.shidduckbook.Fragment.OtherInfoFragment;
import com.shidduckbook.Fragment.Partner_Characterstics_Tab;
import com.shidduckbook.Fragment.PersonalFragment;
import com.shidduckbook.Fragment.Personal_Characterstics_Tab;
import com.shidduckbook.Fragment.Personal_Info_Tab;
import com.shidduckbook.Fragment.PreviewFragment;
import com.shidduckbook.Fragment.ProfileFragment;
import com.shidduckbook.Fragment.ReferencesFragment;
import com.shidduckbook.Fragment.RootFragment;
import com.shidduckbook.Interface.OnBackPressListener;
import com.shidduckbook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        setHasOptionsMenu(false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.getTabAt(0).setText("Personal Info");
        /*tabLayout.getTabAt(1).setText("Personal Traits");
        tabLayout.getTabAt(2).setText("Partner's Traits");*/
        tabLayout.getTabAt(1).setText("Education Info");
        tabLayout.getTabAt(2).setText("Other Info");
        tabLayout.getTabAt(3).setText("References");
        tabLayout.getTabAt(4).setText("Preview");

        return rootView;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    return new Personal_Info_Tab();

                case 1:
                    return new EducationalFragment();
                case 2:
                    return new OtherInfoFragment();
                case 3:
                    return new ReferencesFragment();
                case 4:
                    return new PreviewFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Personal Info";
                case 1:
                    return "Education Info";
                case 2:
                    return "Other Info";
                case 3:
                    return "References";
                case 4:
                    return "Preview";
            }
            return null;
        }
        /*
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }
*/
    }

   /* @Override
    public boolean onBackPressed() {
        OnBackPressListener currentFragment = (OnBackPressListener) mSectionsPagerAdapter.getRegisteredFragment(mViewPager.getCurrentItem());

        if (currentFragment != null) {
            // lets see if the currentFragment or any of its childFragment can handle onBackPressed
            return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }
*/
}
