package com.example.aiutovicino.ui.MyApplicazioni;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aiutovicino.databinding.FragmentMyapplicazioniBinding;
import com.example.aiutovicino.databinding.FragmentPortafoglioBinding;
import com.example.aiutovicino.ui.portafoglio.PortafoglioViewModel;

public class MyApplicazioniFragment extends Fragment {

private FragmentMyapplicazioniBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        MyApplicazioniViewModel myApplicazioniViewModel =
                new ViewModelProvider(this).get(MyApplicazioniViewModel.class);

    binding = FragmentMyapplicazioniBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textMyapplicazioni;
        myApplicazioniViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}