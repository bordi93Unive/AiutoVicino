package com.example.aiutovicino.ui.annuncio_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aiutovicino.databinding.FragmentAnnuncioDetailBinding;

public class AnnuncioDetailFragment extends Fragment {

    private FragmentAnnuncioDetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnnuncioDetailViewModel annuncioDetailViewModel = new ViewModelProvider(this).get(AnnuncioDetailViewModel.class);

        binding = FragmentAnnuncioDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}