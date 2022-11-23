package com.example.aiutovicino.ui.registrazione;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.aiutovicino.databinding.FragmentRegistrazioneBinding;


public class RegistrazioneFragment extends Fragment {

    private FragmentRegistrazioneBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RegistrazioneViewModel registrazioneViewModel = new ViewModelProvider(this).get(RegistrazioneViewModel.class);

        binding = FragmentRegistrazioneBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}