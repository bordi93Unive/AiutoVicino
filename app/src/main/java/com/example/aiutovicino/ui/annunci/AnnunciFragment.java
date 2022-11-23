package com.example.aiutovicino.ui.annunci;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aiutovicino.databinding.FragmentAnnunciBinding;

public class AnnunciFragment extends Fragment {

private FragmentAnnunciBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        AnnunciViewModel annunciViewModel =
                new ViewModelProvider(this).get(AnnunciViewModel.class);

    binding = FragmentAnnunciBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textMyannunci;
        annunciViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}