package com.unive.aiutovicino;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.unive.aiutovicino.databinding.ActivityMainBinding;

import static androidx.fragment.app.FragmentManager.TAG;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("setOnClickListener", "---------- "+view.getTransitionName()+" ----------");
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_services, R.id.nav_score)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.nav_home) {
                    //toolbar.setVisibility(View.GONE);
                    //bottomNavigationView.setVisibility(View.GONE);
                } else {
                    //toolbar.setVisibility(View.VISIBLE);
                    //bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
                return true;
            }
        });




        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {

            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.v("onDestinationChanged", "");
            }

        });


        Button button = (Button) findViewById(R.id.nav_services);

        MenuItem item = (MenuItem) findViewById(R.id.nav_services);



        //item.setOnMenuItemClickListener()

        /*
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // APPUNTI usare solo per login
                //setContentView(R.layout.fragment_home);
                Log.v("--------- onClick", ""+v.getTransitionName());

                Navigation.findNavController(v).navigate(R.id.nav_score, null, null);

            }
        });

         */
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(R.id.nav_home, Menu.NONE, 100, "Item1");
        menu.add(R.id.nav_score, Menu.NONE, 200, "Item2");
        menu.add(R.id.nav_services, Menu.NONE, 300, "Item3");
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
*/
    @Override
    public boolean onSupportNavigateUp() {
        Log.v("onSupportNavigateUp", "#######################################");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.v("onOptionsItemSelected", "#######################################");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.v("onMenuItemClick", ""+item.getItemId());
                // TODO Auto-generated method stub
                return false;
            }
        });
        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_score:
                Toast.makeText(this, "Score selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_services:
                Toast.makeText(this, "Services selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Log.v("onContextItemSelected", "#######################################");
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //Toast.makeText(this, featureId+" onMenuOpened ", Toast.LENGTH_SHORT).show();
        Log.v("onMenuOpened", "#######################################");
        return super.onMenuOpened(featureId, menu);
    }

}