package it.unive.aiutovicino.ui.impostazioni;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
    View root;
    SharedPreferences sharedpreferences;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ImpostazioniViewModel impostazioniViewModel = new ViewModelProvider(this).get(ImpostazioniViewModel.class);
        binding = FragmentImpostazioniBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        General.setSearchViewInvisible();

        sharedpreferences = this.getActivity().getSharedPreferences(General.SHARED_PREFS, binding.getRoot().getContext().MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedpreferences.getString("user", "");

        if(json != null && !json.equals("")) {
            General.user = gson.fromJson(json, UserModel.class);
        }

        progressSpinner = binding.progressBarImpostazioni;

        nome = binding.modNome;
        cognome = binding.modCognome;
        email = binding.modEmail;
        nickname = binding.modNickname;
        password = binding.modPassword;
        descrizione = binding.modDescrizione;



        //UserModel user = UserController.getUserByID(1);
        nome.setText(String.valueOf(General.user.getName()));
        cognome.setText(String.valueOf(General.user.getSurname()));
        email.setText(String.valueOf(General.user.getEmail()));
        nickname.setText(String.valueOf(General.user.getNickname()));
        descrizione.setText(General.user.getDescription());

        //final TextView textView = binding.textImpostazioni;
        //ImpostazioniViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        binding.buttonModDati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nome.getText().toString().trim().isEmpty()) {
                    nome.setError("Compilare il campo nome!");
                    nome.requestFocus();
                    return;
                }
                if(cognome.getText().toString().trim().isEmpty()) {
                    cognome.setError("Compilare il campo cognome!");
                    cognome.requestFocus();
                    return;
                }
                if(email.getText().toString().trim().isEmpty()) {
                    email.setError("Compilare il campo email!");
                    email.requestFocus();
                    return;
                }
                if(nickname.getText().toString().trim().isEmpty()) {
                    nickname.setError("Compilare il campo nickname!");
                    nickname.requestFocus();
                    return;
                }


                new Connection().execute();

            }
        });

        binding.buttonAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_impostazioni_to_nav_home);
            }
        });

        return root;
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

            progressSpinner.setVisibility(View.VISIBLE);
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

        }
    }
}