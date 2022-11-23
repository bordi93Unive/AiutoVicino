package com.example.aiutovicino.ui.login;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.aiutovicino.MainActivity;
import com.example.aiutovicino.R;
import com.example.aiutovicino.controller.UserController;
import com.example.aiutovicino.model.UserModel;
import com.example.aiutovicino.databinding.FragmentLoginBinding;
import com.example.aiutovicino.ui.registrazione.RegistrazioneFragment;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean login = false;
                UserModel user = UserController.getUserByEmail(binding.username.getText().toString());

                if(user != null && user.password.equals(binding.password.getText().toString())){
                    login = true;
                }

                if(!login){
                    Snackbar.make(view, "Utente e/o password errati", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }
        });

        binding.buttonRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_LoginFragment_to_RegistrationFragment);
                //startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        /*final TextView textView = binding.textHome;
        loginViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}