package com.example.aiutovicino.ui.portafoglio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.aiutovicino.databinding.FragmentPortafoglioBinding;

public class PortafoglioFragment extends Fragment {

private FragmentPortafoglioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        PortafoglioViewModel portafoglioViewModel =
                new ViewModelProvider(this).get(PortafoglioViewModel.class);

    binding = FragmentPortafoglioBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textPortafoglio;
        portafoglioViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}