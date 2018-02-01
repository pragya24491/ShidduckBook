package com.shidduckbook.Activity;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.shidduckbook.Fragment.CorouselFragment;
import com.shidduckbook.Fragment.EducationalFragment;
import com.shidduckbook.Fragment.OtherInfoFragment;
import com.shidduckbook.Fragment.PartnerFragment;
import com.shidduckbook.Fragment.Partner_Characterstics_Tab;
import com.shidduckbook.Fragment.PersonalFragment;
import com.shidduckbook.Fragment.Personal_Characterstics_Tab;
import com.shidduckbook.Fragment.Personal_Info_Tab;
import com.shidduckbook.Fragment.PreviewFragment;
import com.shidduckbook.Fragment.ReferencesFragment;
import com.shidduckbook.R;

public class My_Profile extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    CorouselFragment carouselFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__profile);
        getSupportActionBar().setHomeButtonEnabled(true);      // enable the button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // the left caret
        getSupportActionBar().setDisplayShowHomeEnabled(true);

      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

      /*if (savedInstanceState == null) {
            // withholding the previously created fragment from being created again
            // On orientation change, it will prevent fragment recreation
            // its necessary to reserving the fragment stack inside each tab
            initScreen();

        } else {
            // restoring the previously created fragment
            // and getting the reference
            carouselFragment = (CorouselFragment) getSupportFragmentManager().getFragments().get(0);
        }*/

}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           switch (position){

               case 0 :
                   return new Personal_Info_Tab();
               case 1 :
                   return new PersonalFragment();
               case 2 :
                   return new PartnerFragment();
               case 3 :
                   return new EducationalFragment();
               case 4 :
                   return new OtherInfoFragment();
               case 5 :
                   return new ReferencesFragment();
               case 6 :
                   return new PreviewFragment();
               default:
                   return null;
           }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Personal Info";
                case 1:
                    return "Personal Traits";
                case 2:
                    return "Partner's Traits";
                case 3:
                    return "Education Info";
                case 4:
                    return "Other Info";
                case 5:
                    return "References";
                case 6:
                    return "Preview";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
