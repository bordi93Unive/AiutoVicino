package it.unive.aiutovicino.ui.impostazioni;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.AnnuncioController;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentImpostazioniBinding;
import it.unive.aiutovicino.model.AnnuncioModel;
import it.unive.aiutovicino.model.UserModel;
import it.unive.aiutovicino.ui.impostazioni.ImpostazioniViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ImpostazioniFragment extends Fragment {

private FragmentImpostazioniBinding binding;

    EditText nome,cognome,email,nickname,descrizione;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ImpostazioniViewModel impostazioniViewModel = new ViewModelProvider(this).get(ImpostazioniViewModel.class);

        binding = FragmentImpostazioniBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Bundle b = this.getArguments();
        //UserModel user = UserController.getUserByID((int)b.get("idUser"));


        EditText nome = binding.modNome;
        EditText cognome = binding.modCognome;
        EditText email = binding.modEmail;
        EditText nickname = binding.modNickname;
        EditText password = binding.modPassword;
        EditText descrizione = binding.modDescrizione;

        UserModel user = UserController.getUserByID(1);
        nome.setText(String.valueOf(user.name));
        cognome.setText(String.valueOf(user.surname));
        email.setText(String.valueOf(user.email));
        nickname.setText(String.valueOf(user.nickname));
        password.setText(String.valueOf(user.password));
        descrizione.setText("Ugo scrive cose a caso.");

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
                if(password.getText().toString().trim().isEmpty()) {
                    password.setError("Compilare il campo password!");
                    password.requestFocus();
                    return;
                }

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