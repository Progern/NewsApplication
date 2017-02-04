package com.olegmisko.newsapplication.main.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Fragments.WorldNewsPickupFragment;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = "DEFAULT_LOG";
    private  Toolbar toolbar;
    private  DrawerLayout drawer;
    private  NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.openDrawer(GravityCompat.START, true);

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
            Log.d(LOG_TAG, "Action settings pressed");
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
            // TODO: Load business news  pickup fragment
        } else if (id == R.id.nav_sports_news) {
            // TODO: Load sports news  pickup fragment
        } else if (id == R.id.nav_it_news) {
            // TODO: Load IT news  pickup fragment
        } else if (id == R.id.nav_settings) {
            // TODO: Load settings fragment
        } else if (id == R.id.nav_about) {
            // TODO: Load about application fragment
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
