package it.unive.aiutovicino.ui.registrazione;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentRegistrazioneBinding;
import it.unive.aiutovicino.model.UserModel;
import it.unive.aiutovicino.ui.login.LoginFragment;


public class RegistrazioneFragment extends Fragment {

    private FragmentRegistrazioneBinding binding;
    EditText nome,cognome,email,nickname,password;
    View root;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RegistrazioneViewModel registrazioneViewModel = new ViewModelProvider(this).get(RegistrazioneViewModel.class);

        binding = FragmentRegistrazioneBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        nome = binding.nome;
        cognome = binding.cognome;
        email = binding.regEmail;
        nickname = binding.nickname;
        password = binding.regPassword;

        binding.buttonRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        Snackbar.make(root, "Utente registrato", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private class Connection extends AsyncTask {
        @Override
        protected Object doInBackground(Object... arg0){
            UserModel user = new UserModel();
            user.name = nome.getText().toString();
            user.surname = cognome.getText().toString();
            user.email = email.getText().toString();
            user.nickname = nickname.getText().toString();
            user.password = password.getText().toString();

            return UserController.register(user);
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result == null || (Boolean)result == false){
                registerError();
            } else {
                registerOk();
            }
        }
    }
}