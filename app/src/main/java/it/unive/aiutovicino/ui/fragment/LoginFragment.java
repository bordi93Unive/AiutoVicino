package it.unive.aiutovicino.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import it.unive.aiutovicino.General;
import it.unive.aiutovicino.MainActivity;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.model.UserModel;
import it.unive.aiutovicino.databinding.FragmentLoginBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    Activity activity;
    View root;
    EditText username;
    EditText password;
    Button buttonLogin,buttonRegistrati;
    ProgressBar progressSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        username = binding.username;
        password = binding.password;
        buttonLogin = binding.buttonLogin;
        buttonRegistrati = binding.buttonRegistrazione;
        progressSpinner = binding.progressBarLogin;

        activity = this.getActivity();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /** verifiche su campi email e password per la loro corretta compilazione */
                if(username.getText().toString().trim().isEmpty()) {
                    username.setError("Compilare il campo username!");
                    username.requestFocus();
                    return;
                }
                if(password.getText().toString().trim().isEmpty()) {
                    password.setError("Compilare il campo password!");
                    password.requestFocus();
                    return;
                }
                /** a seguito delle opportune verifiche sui campi si effettua verifica
                 *  tramite API della correttezza delle credenziali */
                new Connection().execute();
            }
        });

        buttonRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** navigazione verso il fragment della registrazione*/
                Navigation.findNavController(view).navigate(R.id.action_LoginFragment_to_RegistrationFragment);
            }
        });

        return root;
    }

    private void loadMainActivity(){
        /** avvio activity main*/
        startActivity(new Intent(getActivity(), MainActivity.class));

    }

    private void loginError(){
        /** messaggio di errore generico per indicare fallimento login */
        Snackbar.make(root, "Utente e/o password errati", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class Connection extends AsyncTask {
        @Override
        protected void onPreExecute() {
            /** cambio visibilità progress bar in VISIBLE*/
            progressSpinner.setVisibility(View.VISIBLE);
            buttonLogin.setEnabled(false);
            buttonRegistrati.setEnabled(false);
        }

        @Override
        protected Object doInBackground(Object... arg0){
            return UserController.getAuth(username.getText().toString(), password.getText().toString());
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result == null){
                loginError();
            } else {
                UserModel user = (UserModel)result;
                /** salvataggio dell'oggetto UserModel ottenuto dall'API di Login all'interno
                 *  delle shared preferences */
                General.setUserBySharedPreferences(user, activity, root.getContext().MODE_PRIVATE);
                /** cambio di activity dopo login avvenuta con successo*/
                loadMainActivity();
            }
            /** cambio visibilità progress bar in GONE*/
            progressSpinner.setVisibility(View.GONE);
            buttonLogin.setEnabled(true);
            buttonRegistrati.setEnabled(true);
        }
    }
}