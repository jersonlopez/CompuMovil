package co.edu.udea.compumovil.gr04_20172.lab4;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Navigation_Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Apartment_Fragment.OnFragmentButtonListener{

    Fragment fragment=null;
    Boolean fragmentChoose=false;
    String email;
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    TextView tName, tEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        email = getIntent().getStringExtra("email");
        //Toast.makeText(getApplicationContext(),"ppal " + email, Toast.LENGTH_SHORT).show();
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //nada wey

        fragment=new Apartment_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentNavigation, fragment).commit();



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


        if (id == R.id.itemApartments) {
            // Handle the camera action
            fragment=new Apartment_Fragment();
            fragmentChoose=true;

        } else if (id == R.id.itemProfile) {
            fragment=new Profile_Fragment();
            fragmentChoose=true;
            //transaction.addToBackStack(null);
            //transaction.commit();

        } else if (id == R.id.itemSetting) {
            fragment =new SettingsScreen();
            fragmentChoose=true;


        } else if (id == R.id.itemSign_off) {
            Intent intentLogOut = new Intent(Navigation_Drawer.this, LoginActivity.class);
            startActivity(intentLogOut);
            finish();

        } else if (id == R.id.itemAbout) {
            fragment =new About_Fragment();
            fragmentChoose=true;
        }
        if (fragmentChoose){
            getSupportFragmentManager().beginTransaction().replace(R.id.contentNavigation, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onClick(View view) {
    }

    @Override
    public void onFragmentClickButton(int id) {
        fragment=new FragmentDetail();
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentNavigation,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}
