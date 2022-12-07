package it.unive.aiutovicino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import it.unive.aiutovicino.controller.CategoriaController;
import it.unive.aiutovicino.model.UserModel;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import it.unive.aiutovicino.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**cose da non toccare mai almeno che non sappia cosa stai facendo**/
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        //setContentView((R.layout.fragment_home));
        DrawerLayout drawer = binding.drawerLayout;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        sharedpreferences = getSharedPreferences(General.SHARED_PREFS, binding.getRoot().getContext().MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedpreferences.getString("user", "");

        if(json != null && !json.equals("")) {
            General.user = gson.fromJson(json, UserModel.class);
        }

        if(General.user == null){
            startActivity(new Intent(this, LoginActivity.class));
        }

        setSupportActionBar(binding.appBarMain.toolbar);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_portafoglio, R.id.nav_annunci,R.id.nav_applicazioni,
                R.id.nav_convalida,R.id.nav_impostazioni,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();

       /* navigationView.getMenu().getItem(4).setVisible(false);
        //rendo il menù convalida visibile solo agli admin
        if(General.user.admin)
            navigationView.getMenu().getItem(4).setVisible(true);*/




        View header = navigationView.getHeaderView(0);
        TextView textNameSurname = header.findViewById(R.id.id_badge_user_name_surname);
        TextView textEmail = header.findViewById(R.id.id_badge_user_email);
        ImageView image= header.findViewById(R.id.id_badge_image);

        textNameSurname.setText(General.user.getName() + " " + General.user.getSurname());
        textEmail.setText(General.user.getEmail());

        String mipmapName = "ic_" + General.user.getName().toLowerCase().substring(0,1);

        int resID = getResources().getIdentifier(mipmapName , "mipmap", getPackageName());
        image.setImageResource(resID);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        navigationView.getMenu().getItem(4).setVisible(true);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Barra di ricerca sulla barra
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchview = (SearchView) menuItem.getActionView();
        searchview.setQueryHint("Digita il testo di ricerca");

        //nascondo il menù Convalida e lo mostro solo agli admin
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_convalida).setVisible(false);

        if(General.user.isAdmin()) {
            nav_Menu.findItem(R.id.nav_convalida).setVisible(true);
        }

        new Connection().execute();

        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private class Connection extends AsyncTask {

        @Override
        protected Object doInBackground(Object... arg0)
        {
            General.categorie = CategoriaController.getAllCategories();
            return null;
        }
    }
}