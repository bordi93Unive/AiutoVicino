package com.example.aiutovicino.ui.convalida;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aiutovicino.databinding.FragmentConvalidaBinding;
import com.example.aiutovicino.databinding.FragmentHomeBinding;
import com.example.aiutovicino.ui.home.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ConvalidaFragment extends Fragment {

private FragmentConvalidaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        ConvalidaViewModel convalidaViewModel = new ViewModelProvider(this).get(ConvalidaViewModel.class);

        binding = FragmentConvalidaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}