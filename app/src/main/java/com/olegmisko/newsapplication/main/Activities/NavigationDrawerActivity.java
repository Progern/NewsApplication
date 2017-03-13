package com.olegmisko.newsapplication.main.Activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Config.AppConstants;
import com.olegmisko.newsapplication.main.Fragments.AboutFragment;
import com.olegmisko.newsapplication.main.Fragments.BusinessNewsPickupFragment;
import com.olegmisko.newsapplication.main.Fragments.ItNewsPickupFragment;
import com.olegmisko.newsapplication.main.Fragments.SettingsFragment;
import com.olegmisko.newsapplication.main.Fragments.SportsNewsPickupFragment;
import com.olegmisko.newsapplication.main.Fragments.WelcomeFragment;
import com.olegmisko.newsapplication.main.Fragments.WorldNewsPickupFragment;
import com.olegmisko.newsapplication.main.Services.NewsCheckingService;
import com.olegmisko.newsapplication.main.Services.NotificationHandlerService;
import com.onesignal.OneSignal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    private BroadcastReceiver noNetworkConnectionReceiver;
    private LocalBroadcastManager localBroadcastManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        ButterKnife.bind(this);

        /* Notifications section */
        NotificationHandlerService notificationHandlerService = new NotificationHandlerService(getApplicationContext());
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(notificationHandlerService)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();
        /* End of notifications section */

        /* Navigation drawer section */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*  End of navigation drawer section */

        startService(new Intent(this, NewsCheckingService.class));
        changeFragment(new WelcomeFragment());

        /* Network connection snackbar section */
        final Snackbar networkConnectionBar = Snackbar.make(navigationView, "No internet connection found", Snackbar.LENGTH_INDEFINITE);
        View snackBarView = networkConnectionBar.getView();
        snackBarView.setBackgroundColor(Color.RED);
        TextView snackBarText = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        snackBarText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        /* End of network connection snackbar section  */

        /* Network connection messages receiver section */
        noNetworkConnectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(AppConstants.LOG_TAG, "Message received");
                if (intent.getAction().equals(AppConstants.BROADCAST_ACTION_INTERNET_CONNECTION_APPEARED)) {
                    networkConnectionBar.dismiss();
                } else {
                    networkConnectionBar.show();
                }

            }
        };

        IntentFilter networkConnectionFilter = new IntentFilter();
        networkConnectionFilter.addAction(AppConstants.BROADCAST_ACTION_INTERNET_CONNECTION_APPEARED);
        networkConnectionFilter.addAction(AppConstants.BROADCAST_ACTION_NO_INTERNET_CONNECTION);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(noNetworkConnectionReceiver, networkConnectionFilter);
        /* End of etwork connection messages receiver section*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            /*Do not need to perform any actions
             * on back pressed within main activity
              * for now.
             */
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new SettingsFragment()).commit();
            return true;
        }
        if (id == R.id.log_out) {
            performLogOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_world_news) {
            changeFragment(new WorldNewsPickupFragment());
        } else if (id == R.id.nav_business_news) {
            changeFragment(new BusinessNewsPickupFragment());
        } else if (id == R.id.nav_sports_news) {
            changeFragment(new SportsNewsPickupFragment());
        } else if (id == R.id.nav_it_news) {
            changeFragment(new ItNewsPickupFragment());
        } else if (id == R.id.nav_settings) {
            changeFragment(new SettingsFragment());
        } else if (id == R.id.nav_about) {
            changeFragment(new AboutFragment());
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();
    }

    /* Changes login state and loads authorization activity, also stops the service with notifications */
    private void performLogOut() {
        getSharedPreferences("MainPref", MODE_PRIVATE).edit().putBoolean("Logged_in", false).apply();
        stopService(new Intent(this, NewsCheckingService.class));
        Intent authorizationIntent = new Intent(this, AuthorizationActivity.class);
        startActivity(authorizationIntent);
        overridePendingTransition(R.anim.alpha, R.anim.alpha_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
        localBroadcastManager.unregisterReceiver(noNetworkConnectionReceiver);
    }
}
