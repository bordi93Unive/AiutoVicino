package it.unive.aiutovicino;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import it.unive.aiutovicino.controller.CategoryController;
import it.unive.aiutovicino.model.UserModel;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import it.unive.aiutovicino.databinding.ActivityMainBinding;
import it.unive.aiutovicino.ui.SearchViewModel;

import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private SearchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**cose da non toccare mai almeno che non sappia cosa stai facendo**/
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        //setContentView((R.layout.fragment_home));
        DrawerLayout drawer = binding.drawerLayout;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        UserModel user = General.getUserBySharedPreferences(this, binding.getRoot().getContext().MODE_PRIVATE);

        /** Se le informazioni relative all'utente non sono presenti rimanda alla login */
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        setSupportActionBar(binding.appBarMain.toolbar);


        /** Inizializza il menù di navigazione laterale */
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_portafoglio, R.id.nav_annunci, R.id.nav_applicazioni,
                R.id.nav_convalida, R.id.nav_impostazioni, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();

        /** Imposta il nome utente, foto e la relativa email nel menù di navigazione*/
        View header = navigationView.getHeaderView(0);
        TextView textNameSurname = header.findViewById(R.id.id_badge_user_name_surname);
        TextView textEmail = header.findViewById(R.id.id_badge_user_email);
        ImageView image = header.findViewById(R.id.id_badge_image);

        textNameSurname.setText(user.getName() + " " + user.getSurname());
        textEmail.setText(user.getEmail());
        String mipmapName = "ic_" + user.getName().toLowerCase().substring(0, 1);
        int resID = getResources().getIdentifier(mipmapName, "mipmap", getPackageName());
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
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Digita il testo di ricerca");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.setFilter(newText);
                return false;
            }
        });

        /** nasconde il menù Convalida in quanto visibile solamente all'utente avente true nell'attributo admin */
        //nascondo
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_convalida).setVisible(false);

        if(General.user.isAdmin()) {
            nav_Menu.findItem(R.id.nav_convalida).setVisible(true);
        }
        /** esecuzione di chiamate all'API Category per ottenerne la lista */
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
            General.categories = CategoryController.getAllCategories();
            return null;
        }
    }
}