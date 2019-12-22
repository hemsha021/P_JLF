package com.applet.jlf;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.applet.jlf.Activity.About;
import com.applet.jlf.Activity.Tutorial;
import com.applet.jlf.Fragment.Nearby;
import com.applet.jlf.Fragment.Route;
import com.applet.jlf.Fragment.Stop;
import com.applet.jlf.Fragment.Trip;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Nearby.OnFragmentInteractionListener, Route.OnFragmentInteractionListener, Stop.OnFragmentInteractionListener, Trip.OnFragmentInteractionListener
{

    Fragment fragment = null;
    Class fragmentClass;

    static LinearLayout customToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  /*      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
*/

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        customToolbar = findViewById(R.id.customToolbar);
        customToolbar.animate().translationY(-(2 * customToolbar.getHeight())).setStartDelay(10).setDuration(30).start();

        ImageButton drawerBtn = findViewById(R.id.drawerbtn);
        drawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.START);
            }
        });

        NavigationView navigationView =(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentClass = Nearby.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.replacable, fragment).commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.about){
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }else if(id==R.id.support){

            Intent email = new Intent(Intent.ACTION_SEND);
            email.setData(Uri.parse("mailto:"));
            email.setType("text/plain");

            email.putExtra(Intent.EXTRA_EMAIL,new String[]{"hemsha009@gmail.com"});


            try{
                startActivity(Intent.createChooser(email,"Choose an Email Client"));
            }catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }else if(id==R.id.tutorial){
            Intent intent = new Intent(MainActivity.this, Tutorial.class);
            startActivity(intent);
        }else{
            changeFragment(item);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nearby:
                //fragmentNumber =0;
                fragmentClass = Nearby.class;
                break;
            case R.id.route:
                //fragmentNumber =0;
                fragmentClass = Route.class;
                break;
            case R.id.stop:
                //fragmentNumber =0;
                fragmentClass = Stop.class;
                break;
            case R.id.trip:
                //fragmentNumber = 0;
                fragmentClass = Trip.class;
                break;
            default:
                fragmentClass = Nearby.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            //frag = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.replacable, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static  LinearLayout getCustomToolbar(){
        return customToolbar;
    }
}
