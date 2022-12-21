package it.unive.aiutovicino.ui.fragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentImpostazioniBinding;
import it.unive.aiutovicino.model.UserModel;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

public class ImpostazioniFragment extends Fragment {

private FragmentImpostazioniBinding binding;

    EditText nome,cognome,email,nickname,descrizione,password;
    ProgressBar progressSpinner;
    Button buttonModDati,buttonAnnulla;
    View root;
    SharedPreferences sharedpreferences;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImpostazioniBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        setHasOptionsMenu(true);
        sharedpreferences = this.getActivity().getSharedPreferences(General.SHARED_PREFS, binding.getRoot().getContext().MODE_PRIVATE);

        if(General.checkTokenExpiration()){
            NavController n = findNavController(this);
            n.navigate(R.id.action_nav_home_to_nav_logout);
        }
        else {

            Gson gson = new Gson();
            String json = sharedpreferences.getString("user", "");

            if (json != null && !json.equals("")) {
                General.user = gson.fromJson(json, UserModel.class);
            }

            progressSpinner = binding.progressBarImpostazioni;

            nome = binding.modNome;
            cognome = binding.modCognome;
            email = binding.modEmail;
            nickname = binding.modNickname;
            password = binding.modPassword;
            descrizione = binding.modDescrizione;
            buttonModDati = binding.buttonModDati;
            buttonAnnulla = binding.buttonAnnulla;


            nome.setText(String.valueOf(General.user.getName()));
            cognome.setText(String.valueOf(General.user.getSurname()));
            email.setText(String.valueOf(General.user.getEmail()));
            nickname.setText(String.valueOf(General.user.getNickname()));
            descrizione.setText(General.user.getDescription());

<<<<<<< Updated upstream
            buttonModDati.setOnClickListener(new View.OnClickListener() {
=======
            binding.buttonEyeImpostazioni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    else
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            });

            binding.buttonModDati.setOnClickListener(new View.OnClickListener() {
>>>>>>> Stashed changes
                @Override
                public void onClick(View view) {

                    if (nome.getText().toString().trim().isEmpty()) {
                        nome.setError("Compilare il campo nome!");
                        nome.requestFocus();
                        return;
                    }
                    if (cognome.getText().toString().trim().isEmpty()) {
                        cognome.setError("Compilare il campo cognome!");
                        cognome.requestFocus();
                        return;
                    }
                    if (email.getText().toString().trim().isEmpty()) {
                        email.setError("Compilare il campo email!");
                        email.requestFocus();
                        return;
                    }
                    if (nickname.getText().toString().trim().isEmpty()) {
                        nickname.setError("Compilare il campo nickname!");
                        nickname.requestFocus();
                        return;
                    }
                    if(!password.getText().toString().isEmpty()){
                        if(!General.isValid(password.getText().toString())) {
                            password.setError("La password non rispetta i requisiti minimi di sicurezza!");
                            password.requestFocus();
                            return;
                        }
                    }


                    new Connection().execute();

                }
            });

            buttonAnnulla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_nav_impostazioni_to_nav_home);
                }
            });
        }
        return root;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_search);
        if(item!=null)
            item.setVisible(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateError(){
        Snackbar.make(root, "Errore durante il cambio dati", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void updateok(){
        Snackbar.make(root, "Dati aggiornati correttamente", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    private class Connection extends AsyncTask {

        @Override
        protected void onPreExecute() {
            //mostra lo spinner di loading e blocco i bottoni
            progressSpinner.setVisibility(View.VISIBLE);
            buttonModDati.setEnabled(false);
            buttonAnnulla.setEnabled(false);
        }

        @Override
        protected Object doInBackground(Object... arg0){
            General.user.setName(nome.getText().toString());
            General.user.setSurname(cognome.getText().toString());
            General.user.setEmail(email.getText().toString().trim());
            General.user.setNickname(nickname.getText().toString().trim());
            if(!password.getText().toString().trim().isEmpty())
                General.user.setPassword(password.getText().toString().trim());
            General.user.setDescription(descrizione.getText().toString());

            return UserController.update(General.user);
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result == null || (Boolean)result == false){
                updateError();
            } else {
                updateok();
                //reindirizzo al fragment home
                Navigation.findNavController(getView()).navigate(R.id.action_nav_impostazioni_to_nav_home);
            }

            progressSpinner.setVisibility(View.GONE);
            buttonModDati.setEnabled(true);
            buttonAnnulla.setEnabled(true);

        }
    }
}