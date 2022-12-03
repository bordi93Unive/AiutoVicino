package it.unive.aiutovicino.ui.login;

import static androidx.navigation.fragment.FragmentKt.findNavController;

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
import androidx.navigation.Navigation;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.MainActivity;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.model.UserModel;
import it.unive.aiutovicino.databinding.FragmentLoginBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    SharedPreferences sharedpreferences;
    View root;
    EditText username;
    EditText password;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        username = binding.username;
        password = binding.password;

        sharedpreferences = this.getActivity().getSharedPreferences(General.SHARED_PREFS, root.getContext().MODE_PRIVATE);


        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Connection().execute();
            }
        });



        binding.buttonRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_LoginFragment_to_RegistrationFragment);
            }
        });

        return root;
    }

    private void loadMainActivity(){
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    private void loginError(){
        Snackbar.make(root, "Utente e/o password errati", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class Connection extends AsyncTask {
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

                SharedPreferences.Editor editor = sharedpreferences.edit();

                Gson gson = new Gson();
                String json = gson.toJson(user);
                editor.putString("user", json);

                editor.apply();
                //UserPractice.getInstance().saveUser(user, binding.getRoot().getContext());
                loadMainActivity();
            }
        }
    }
}