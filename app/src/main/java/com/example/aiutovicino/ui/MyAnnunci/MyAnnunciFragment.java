package com.example.aiutovicino.ui.MyAnnunci;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.aiutovicino.databinding.FragmentMyannunciBinding;

public class MyAnnunciFragment extends Fragment {

private FragmentMyannunciBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        MyAnnunciViewModel myAnnunciViewModel =
                new ViewModelProvider(this).get(MyAnnunciViewModel.class);

    binding = FragmentMyannunciBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textMyannunci;
        myAnnunciViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}