package it.unive.aiutovicino.ui.applicazioni;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import it.unive.aiutovicino.databinding.FragmentApplicazioniBinding;

public class ApplicazioniFragment extends Fragment {

private FragmentApplicazioniBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        ApplicazioniViewModel applicazioniViewModel =
                new ViewModelProvider(this).get(ApplicazioniViewModel.class);

    binding = FragmentApplicazioniBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textMyapplicazioni;
        applicazioniViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}