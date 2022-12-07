package it.unive.aiutovicino.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
    SharedPreferences sharedPreferences;
    View root;
    EditText username;
    EditText password;
    ProgressBar progressSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        username = binding.username;
        password = binding.password;
        progressSpinner = binding.progressBarLogin;

        /** inizializzazione delle shared preferences per eventuale utilizzo in caso
         *  di corretta login da parte dell'utente */
        sharedPreferences = this.getActivity().getSharedPreferences(General.SHARED_PREFS, root.getContext().MODE_PRIVATE);


        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
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

        binding.buttonRegistrazione.setOnClickListener(new View.OnClickListener() {
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
        Snackbar.make(root, "Utente e/o password errati", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class Connection extends AsyncTask {
        //ProgressDialog progDailog;

        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
            //se voglio fare il loading in modo diverso con la scritta Loading che compare usare il codice qui sotto
            /*progDailog = new ProgressDialog(getContext());
            progDailog.setMessage("Loading");
            progDailog.setCancelable(false);
            progDailog.show();*/
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(user);
                editor.putString("user", json);
                editor.apply();
                /** cambio di activity dopo login avvenuta con successo*/
                loadMainActivity();
            }
            /** cambio visibilità progress bar in GONE*/
            progressSpinner.setVisibility(View.GONE);
        }
    }
}