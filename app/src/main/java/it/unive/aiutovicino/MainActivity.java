package it.unive.aiutovicino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.model.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.xml.sax.SAXException;

import java.io.IOException;

import it.unive.aiutovicino.databinding.ActivityMainBinding;
import it.unive.aiutovicino.task.AnnuncioTask;
import it.unive.aiutovicino.ui.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**cose da non toccare mai almeno che non sappia cosa stai facendo**/
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        sharedpreferences = getSharedPreferences(General.SHARED_PREFS, binding.getRoot().getContext().MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedpreferences.getString("user", "");

        if(json != null && !json.equals("")) {
            General.user = gson.fromJson(json, UserModel.class);
        }

        if(General.user == null){
            startActivity(new Intent(this, LoginActivity.class));
        }


        setContentView(binding.getRoot());
        //setContentView((R.layout.fragment_home));
        setSupportActionBar(binding.appBarMain.toolbar);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_portafoglio, R.id.nav_annunci,R.id.nav_applicazioni,
                R.id.nav_convalida,R.id.nav_impostazioni,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();


        View header = navigationView.getHeaderView(0);
        TextView textNameSurname = header.findViewById(R.id.id_badge_user_name_surname);
        TextView textEmail = header.findViewById(R.id.id_badge_user_email);
        ImageView image= header.findViewById(R.id.id_badge_image);

        textNameSurname.setText(General.user.name + " " + General.user.surname);
        textEmail.setText(General.user.email);

        String mipmapName = "ic_" + General.user.name.toLowerCase().substring(0,1);

        int resID = getResources().getIdentifier(mipmapName , "mipmap", getPackageName());
        image.setImageResource(resID);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    /**azione che scateno quando viene premuto l'hamburger. Se lo tolgo non va l'hamburger*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}