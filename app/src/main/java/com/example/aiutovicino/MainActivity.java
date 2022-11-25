package com.example.aiutovicino;

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

import com.example.aiutovicino.controller.UserController;
import com.example.aiutovicino.model.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aiutovicino.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**cose da non toccare mai almeno che non sappia cosa stai facendo**/
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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


        UserModel user = UserController.getUserByID(1);

        View header = navigationView.getHeaderView(0);
        TextView textNameSurname = header.findViewById(R.id.id_badge_user_name_surname);
        TextView textEmail = header.findViewById(R.id.id_badge_user_email);
        ImageView image= header.findViewById(R.id.id_badge_image);

        textNameSurname.setText(user.name + " " + user.surname);
        textEmail.setText(user.email);


      /*  final Uri uri = Uri.parse("./mipmap/ic_a/ic_a.png");
        final String path = uri.getPath();
        final Drawable drawable = Drawable.createFromPath(path);
        image.setImageDrawable(drawable);*/



        // TextDrawable drawable = TextDrawable.builder().buildRect("A", Color.RED);
       /* String nome = user.name.toLowerCase().substring(0,1);
        Drawable drawable = Drawable.createFromPath("@mipmap/ic_" + nome);
        ImageView imageView = (ImageView) findViewById();

        image.setImageResource(R.mipmap.ic_b);*/
        //imageView.setImageDrawable*/
        String nome = "ic_" + user.name.toLowerCase().substring(0,1);
        //Resources re = null;
        ImageView imageView = (ImageView) findViewById(R.id.id_badge_image);
        int imageId = getResources().getIdentifier(nome, "mipmap", getPackageName());

        if (imageId > 0) {
            imageView.setImageResource(imageId);
        }



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