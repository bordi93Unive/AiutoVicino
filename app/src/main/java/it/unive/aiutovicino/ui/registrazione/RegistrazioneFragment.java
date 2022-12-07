package it.unive.aiutovicino.ui.registrazione;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentRegistrazioneBinding;
import it.unive.aiutovicino.model.UserModel;
import it.unive.aiutovicino.ui.login.LoginFragment;


public class RegistrazioneFragment extends Fragment {

    private FragmentRegistrazioneBinding binding;
    EditText nome,cognome,email,nickname,password;
    View root;
    ProgressBar progressSpinner;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RegistrazioneViewModel registrazioneViewModel = new ViewModelProvider(this).get(RegistrazioneViewModel.class);

        binding = FragmentRegistrazioneBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        progressSpinner = binding.progressBarRegistrazione;

        nome = binding.nome;
        cognome = binding.cognome;
        email = binding.regEmail;
        nickname = binding.nickname;
        password = binding.regPassword;


        binding.buttonRegistrati.setOnClickListener(new View.OnClickListener() {
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
                if(password.getText().toString().trim().isEmpty()) {
                    password.setError("Compilare il campo password!");
                    password.requestFocus();
                    return;
                }

                new Connection().execute();

            }

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void registerError(){
        Snackbar.make(root, "Errore durante la registrazione", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void registerOk(){
        /*ProgressDialog progDailog = new ProgressDialog(getContext());
        progDailog.setMessage("Richiesta di registrazione effettuata");
        progDailog.show();*/
        Snackbar.make(root, "Richiesta di registrazione effettuata", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private class Connection extends AsyncTask {

        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0){
            UserModel user = new UserModel();
            user.setName(nome.getText().toString());
            user.setSurname(cognome.getText().toString());
            user.setEmail(email.getText().toString());
            user.setNickname(nickname.getText().toString());
            user.setPassword(password.getText().toString());

            return UserController.register(user);
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result == null || (Boolean)result == false){
                registerError();
            } else {
                registerOk();
                //reindirizzo al fragment di login
                Navigation.findNavController(getView()).navigate(R.id.action_RegistrazioneFragment_to_LoginFragment);
            }

            progressSpinner.setVisibility(View.GONE);

        }
    }
}