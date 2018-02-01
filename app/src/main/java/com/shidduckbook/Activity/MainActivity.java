package com.shidduckbook.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.shidduckbook.Adapter.MainAdapter;
import com.shidduckbook.Fragment.ArchiveFragment;
import com.shidduckbook.Fragment.EditProfileFragment;
import com.shidduckbook.Fragment.FavoriteFragment;
import com.shidduckbook.Fragment.MainFragment;
import com.shidduckbook.Fragment.MyInterestFragment;
import com.shidduckbook.Fragment.MyMatchesFragment;
import com.shidduckbook.Fragment.MyNotesFragment;
import com.shidduckbook.Fragment.MyProfileFragment;
import com.shidduckbook.R;
import com.shidduckbook.Util.AppPreferences;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements /*ProfileFragment.OnFragmentInteractionListener
        , */NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    public static CircleImageView userImageView;
    LinearLayout nav_header_linearLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    MainAdapter adapter;
    FloatingActionMenu floatingActionMenu;
    EditText editText;
    private String gender;
    private TextView nameTextView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton ageButton, locationButton, backgroundButton, statusButton, filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");

        initFabs();
        adapter = new MainAdapter();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        View header = mNavigationView.getHeaderView(0);

        MenuItem item0, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11;

        item0 = mNavigationView.getMenu().getItem(0);  // Home
        item1 = mNavigationView.getMenu().getItem(1);  // My Playlist & account
        item2 = mNavigationView.getMenu().getItem(2);  // Players
        item3 = mNavigationView.getMenu().getItem(3);  // Call/Email
        item4 = mNavigationView.getMenu().getItem(4);  // Login
        item5 = mNavigationView.getMenu().getItem(5);  // Settings
        item6 = mNavigationView.getMenu().getItem(6);  // Logout
        item7 = mNavigationView.getMenu().getItem(7);  // Logout
        item8 = mNavigationView.getMenu().getItem(8);  // Logout
        item9 = mNavigationView.getMenu().getItem(9);  // Logout
        item10 = mNavigationView.getMenu().getItem(10);  // Logout
        item11 = mNavigationView.getMenu().getItem(11);  // Logout

        if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
//            item0.setVisible(false);
//            item1.setVisible(false);
//            item2.setVisible(false);
//            item3.setVisible(false);
//            item4.setVisible(false);
//            item5.setVisible(false);
//            item6.setVisible(false);
//            item7.setVisible(false);
//            item8.setVisible(false);
            item9.setVisible(true);
//            item10.setVisible(false);
            item11.setVisible(false);

        } else {
            /*item0.setVisible(true);
            item1.setVisible(true);
            item2.setVisible(true);
            item3.setVisible(true);
            item4.setVisible(true);
            item5.setVisible(true);
            item6.setVisible(true);
            item7.setVisible(true);
            item8.setVisible(true);
            item10.setVisible(true);*/
            item9.setVisible(false);
            item11.setVisible(true);
        }

        /*
        *    NAVIGATION DRAWER ITEM --- IMAGE AND TEXT VIEW
        * */

        nav_header_linearLayout = (LinearLayout) header.findViewById(R.id.nav_header_linearLayout);
        userImageView = (CircleImageView) header.findViewById(R.id.userImageView);
        nameTextView = (TextView) header.findViewById(R.id.nameTextView);
        if (!AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
            nameTextView.setText(AppPreferences.getFirstName(MainActivity.this) + " " + AppPreferences.getLastName(MainActivity.this));

            if (!AppPreferences.getUserImage(MainActivity.this).equalsIgnoreCase(""))
                Picasso.with(MainActivity.this).load(AppPreferences.getUserImage(MainActivity.this))
                        .placeholder(R.drawable.pro_icon)
                        .error(R.drawable.pro_icon)
                        .resize(200, 200)
                        .into(userImageView);
        } else {
            nameTextView.setText("");
        }

         /*
        *    NAVIGATION DRAWER ITEM --- IMAGE AND TEXT VIEW
        * */

        if (getIntent().getAction().equalsIgnoreCase("HomeActivity")) {
            Log.v(TAG, "Inside without login !!!");
            gender = getIntent().getExtras().getString("gender");
        }

        initFragment();
        setListener();

    }


    private void initFabs() {

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionMenu.setClosedOnTouchOutside(true);

        ageButton = (FloatingActionButton) findViewById(R.id.ageButton);
        locationButton = (FloatingActionButton) findViewById(R.id.locationButton);
        backgroundButton = (FloatingActionButton) findViewById(R.id.backgroundButton);
        statusButton = (FloatingActionButton) findViewById(R.id.statusButton);
        filterButton = (FloatingActionButton) findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                floatingActionMenu.close(true);
                if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
                    loginDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this, TraitsFilterActivity.class);
                    startActivity(intent);
                }
            }
        });


        ageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
                    loginDialog();
                } else {

                }
            }
        });


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
                    loginDialog();
                } else {

                }

            }
        });


        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
                    loginDialog();
                } else {

                }

            }
        });


    }

    private void initFragment() {

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        FragmentTransaction fragmentTransaction;
        Fragment argumentFragment = new MainFragment();
        //Use bundle to pass data
        Bundle data = new Bundle();
        data.putString("gender", gender);
        argumentFragment.setArguments(data);
        fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerView, argumentFragment).commit();
        toolbar.setTitle("Home");
    }

    private void setListener() {

        nav_header_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {

                    loginDialog();

                } else {
                    hideFloatingActionButton();
                    Log.v(TAG, "Inside Navigation header image view!!!");
                    FragmentTransaction fragmentTransaction;
                    Fragment argumentFragment = new EditProfileFragment();
                    fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, argumentFragment).commit();
                    toolbar.setTitle("Edit Profile");
//                  toggle.setHomeAsUpIndicator(R.drawable.arrow_back_white);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            floatingActionMenu.close(true);
            return true;
        } else if (id == R.id.action_search) {
            floatingActionMenu.close(true);
//            searchView.openSearch();

        } else if (id == R.id.action_share) {
            floatingActionMenu.close(true);
            String shareBody = "Download Shidduch Book app to search your partner !!! ";

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/html");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));

        } else if (id == R.id.action_adduser) {
            floatingActionMenu.close(true);
            if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
                loginDialog();
            } else {
                Intent in = new Intent(MainActivity.this, Add_New_User.class);
                startActivity(in);
            }

        } else if (id == R.id.action_archieve) {

            floatingActionMenu.close(true);
            if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
                loginDialog();
            } else {
                hideFloatingActionButton();
                FragmentTransaction fragmentTransaction;
                Fragment argumentFragment = new ArchiveFragment();
                fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, argumentFragment).commit();
                toolbar.setTitle("Archive");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_profile) {
            floatingActionMenu.close(true);
            if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
                loginDialog();
            } else {
                /*
                 Intent in = new Intent(MainActivity.this, TraitsFilterActivity.class);
                startActivity(in);
                */
                hideFloatingActionButton();
                FragmentTransaction fragmentTransaction;
                Fragment argumentFragment = new MyProfileFragment();
                fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, argumentFragment).commit();
                toolbar.setTitle("My Profile");
            }
            // Handle the camera action
        } else if (id == R.id.nav_home) {

            showFloatingActionButton();
            floatingActionMenu.close(true);

           /* if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
                HomeActivity.homePage = true;
                FragmentTransaction fragmentTransaction;
                Fragment argumentFragment = new MainFragment();
                //Use bundle to pass data
                Bundle data = new Bundle();
                data.putString("gender", gender);
                argumentFragment.setArguments(data);
                fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, argumentFragment).commit();
                toolbar.setTitle("Home");
            } else {*/

            FragmentTransaction fragmentTransaction;
            Fragment argumentFragment = new MainFragment();
            //Use bundle to pass data
            Bundle data = new Bundle();
            data.putString("gender", gender);
            argumentFragment.setArguments(data);
            fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, argumentFragment).commit();
            toolbar.setTitle("Home");
//            }

        } else if (id == R.id.nav_fav) {
            hideFloatingActionButton();
            floatingActionMenu.close(true);
            if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {

                loginDialog();

            } else {

                hideFloatingActionButton();
                FragmentTransaction fragmentTransaction;
                Fragment argumentFragment = new FavoriteFragment();
                fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, argumentFragment).commit();
                toolbar.setTitle("Star");
            }

        } else if (id == R.id.nav_interest) {

            hideFloatingActionButton();
            floatingActionMenu.close(true);

            if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {

                loginDialog();

            } else {

                hideFloatingActionButton();
                FragmentTransaction fragmentTransaction;
                Fragment argumentFragment = new MyInterestFragment();
                fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, argumentFragment).commit();
                toolbar.setTitle("My Interest");
            }

        } else if (id == R.id.nav_matches) {

            hideFloatingActionButton();
            floatingActionMenu.close(true);
            if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {

                loginDialog();

            } else {
                hideFloatingActionButton();
                FragmentTransaction fragmentTransaction;
                Fragment argumentFragment = new MyMatchesFragment();
                fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, argumentFragment).commit();
                toolbar.setTitle("My Match");

            }

        } else if (id == R.id.nav_resume) {

            hideFloatingActionButton();
            floatingActionMenu.close(true);
            if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {

                loginDialog();

            } else {

            }

        } else if (id == R.id.nav_notes) {
            floatingActionMenu.close(true);
            if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {

                loginDialog();

            } else {
                hideFloatingActionButton();
                FragmentTransaction fragmentTransaction;
                Fragment argumentFragment = new MyNotesFragment();
                fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, argumentFragment).commit();
                toolbar.setTitle("My Notes");
            }

        } else if (id == R.id.nav_intro) {
            floatingActionMenu.close(true);

            hideFloatingActionButton();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (id == R.id.nav_pause) {
            floatingActionMenu.close(true);
            if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
                loginDialog();
            } else {

            }

        } else if (id == R.id.nav_share) {
            floatingActionMenu.close(true);
            if (AppPreferences.getUserId(MainActivity.this).equalsIgnoreCase("")) {
                loginDialog();
            } else {

            }

        } else if (id == R.id.nav_logout) {
            floatingActionMenu.close(true);
            logoutDialog();

        } else if (id == R.id.nav_login) {
            floatingActionMenu.close(true);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        return true;
    }

    public void loginDialog() {

        final Dialog markerDialog = new Dialog(MainActivity.this);
        markerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = markerDialog.getWindow();
//        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_dialog));
        window.setGravity(Gravity.CENTER);

        markerDialog.setContentView(R.layout.logout_popup);
        final Button btn_done = (Button) markerDialog.findViewById(R.id.done_btn);
        final Button btn_cancel = (Button) markerDialog.findViewById(R.id.cancel_btn);

        final TextView text2 = (TextView) markerDialog.findViewById(R.id.texttwo);
        text2.setText("You are not logged in. Please Login.");
        //String[] ITEMS = getResources().getStringArray(R.array.country);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btn_cancel.setBackgroundResource(R.drawable.buttonshape);
//                btn_done.setBackgroundResource(R.drawable.buttonshape_two);

                markerDialog.dismiss();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                btn_cancel.setBackgroundResource(R.drawable.buttonshape_two);
//                btn_done.setBackgroundResource(R.drawable.buttonshape);
                markerDialog.dismiss();
            }
        });

        markerDialog.setCanceledOnTouchOutside(false);
        markerDialog.show();
    }

    public void logoutDialog() {

        final Dialog markerDialog = new Dialog(this);
        markerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = markerDialog.getWindow();
//        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_dialog));
        window.setGravity(Gravity.CENTER);

        markerDialog.setContentView(R.layout.logout_popup);
        final Button btn_done = (Button) markerDialog.findViewById(R.id.done_btn);
        final Button btn_cancel = (Button) markerDialog.findViewById(R.id.cancel_btn);

        final TextView text2 = (TextView) markerDialog.findViewById(R.id.texttwo);
        //String[] ITEMS = getResources().getStringArray(R.array.country);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                btn_cancel.setBackgroundResource(R.drawable.buttonshape);
//                btn_done.setBackgroundResource(R.drawable.buttonshape_two);

                AppPreferences.setUserId(MainActivity.this, "");
                AppPreferences.setFirstName(MainActivity.this, "");
                AppPreferences.setLastName(MainActivity.this, "");
                AppPreferences.setEmailId(MainActivity.this, "");
                AppPreferences.setFatherName(MainActivity.this, "");
                AppPreferences.setMotherName(MainActivity.this, "");
                AppPreferences.setGender(MainActivity.this, "");
                AppPreferences.setUserImage(MainActivity.this, "");
                AppPreferences.setProfilePicturePrivacy(MainActivity.this, "");
                AppPreferences.setImageExpansionPrivacy(MainActivity.this, "");
                AppPreferences.setPartnerPreferenceStatus(MainActivity.this, "");
                AppPreferences.setReferenceFriendRelation2(MainActivity.this, "");

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                markerDialog.dismiss();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                btn_cancel.setBackgroundResource(R.drawable.buttonshape_two);
//                btn_done.setBackgroundResource(R.drawable.buttonshape);
                markerDialog.dismiss();
            }
        });

        markerDialog.setCanceledOnTouchOutside(false);
        markerDialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        floatingActionMenu.close(true);
        floatingActionMenu.setClosedOnTouchOutside(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        floatingActionMenu.close(true);
    }

    public void showFloatingActionButton() {
        floatingActionMenu.showMenu(false);
    }

    ;

    public void hideFloatingActionButton() {
        floatingActionMenu.hideMenu(false);
    }

    ;
}
