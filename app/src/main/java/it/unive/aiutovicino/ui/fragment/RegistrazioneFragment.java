package it.unive.aiutovicino.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentRegistrazioneBinding;
import it.unive.aiutovicino.model.UserModel;


public class RegistrazioneFragment extends Fragment {

    private FragmentRegistrazioneBinding binding;
    private EditText nome,cognome,email,nickname,password;
    private View root;
    private ProgressBar progressSpinner;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrazioneBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        progressSpinner = binding.progressBarRegistrazione;

        nome = binding.nome;
        cognome = binding.cognome;
        email = binding.regEmail;
        nickname = binding.nickname;
        password = binding.regPassword;

        binding.buttonEyeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    binding.buttonEyeRegister.setImageResource(R.drawable.ic_password_eye_off);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    binding.buttonEyeRegister.setImageResource(R.drawable.ic_password_eye);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        binding.buttonInfoPswRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                General.showAlertDialog(view);
            }
        });


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
                if(password.getText().toString().isEmpty()) {
                    password.setError("Compilare il campo password!");
                    password.requestFocus();
                    return;
                }
               if(!General.isValid(password.getText().toString())){
                    password.setError("La password non rispetta i requisiti minimi di sicurezza!");
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