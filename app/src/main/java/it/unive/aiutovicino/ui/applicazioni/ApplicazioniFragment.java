package it.unive.aiutovicino.ui.applicazioni;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import it.unive.aiutovicino.databinding.FragmentApplicazioniBinding;
import it.unive.aiutovicino.ui.SearchViewModel;

public class ApplicazioniFragment extends Fragment {

private FragmentApplicazioniBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel viewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        binding = FragmentApplicazioniBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMyapplicazioni;
        viewModel.getFilter().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
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