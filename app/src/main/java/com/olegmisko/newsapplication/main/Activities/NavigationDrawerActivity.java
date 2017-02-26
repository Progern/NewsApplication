package com.olegmisko.newsapplication.main.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Fragments.BusinessNewsPickupFragment;
import com.olegmisko.newsapplication.main.Fragments.ItNewsPickupFragment;
import com.olegmisko.newsapplication.main.Fragments.SettingsFragment;
import com.olegmisko.newsapplication.main.Fragments.SportsNewsPickupFragment;
import com.olegmisko.newsapplication.main.Fragments.WorldNewsPickupFragment;
import com.olegmisko.newsapplication.main.Services.NotificationHandlerService;
import com.onesignal.OneSignal;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = "DEFAULT_LOG";
    private  Toolbar toolbar;
    private  DrawerLayout drawer;
    private  NavigationView navigationView;
    private NotificationHandlerService notificationHandlerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        notificationHandlerService = new NotificationHandlerService(getApplicationContext());
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(notificationHandlerService)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
              * TODO: Perform a two-step application quit*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new SettingsFragment()).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_world_news) {
            // TODO: Load world news pickup fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new WorldNewsPickupFragment()).commit();
        } else if (id == R.id.nav_business_news) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new BusinessNewsPickupFragment()).commit();
        } else if (id == R.id.nav_sports_news) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new SportsNewsPickupFragment()).commit();
        } else if (id == R.id.nav_it_news) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new ItNewsPickupFragment()).commit();
        } else if (id == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new SettingsFragment()).commit();
        } else if (id == R.id.nav_about) {
            // TODO: Load about application fragment
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
