package com.example.aiutovicino.ui.impostazioni;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.aiutovicino.R;
import com.example.aiutovicino.controller.AnnuncioController;
import com.example.aiutovicino.controller.UserController;
import com.example.aiutovicino.databinding.FragmentImpostazioniBinding;
import com.example.aiutovicino.model.AnnuncioModel;
import com.example.aiutovicino.model.UserModel;
import com.example.aiutovicino.ui.impostazioni.ImpostazioniViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ImpostazioniFragment extends Fragment {

private FragmentImpostazioniBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        //ImpostazioniViewModel impostazioniViewModel = new ViewModelProvider(this).get(ImpostazioniViewModel.class);

        binding = FragmentImpostazioniBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Bundle b = this.getArguments();
        //UserModel user = UserController.getUserByID((int)b.get("idUser"));
        UserModel user = UserController.getUserByID(1);
        binding.modNome.setText(String.valueOf(user.name));
        binding.modCognome.setText(String.valueOf(user.surname));
        binding.modEmail.setText(String.valueOf(user.email));
        binding.modNickname.setText(String.valueOf(user.nickname));

        //final TextView textView = binding.textImpostazioni;
        //ImpostazioniViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            binding.buttonModDati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Dati aggiornati", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Navigation.findNavController(view).navigate(R.id.action_nav_impostazioni_to_nav_home);

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
}