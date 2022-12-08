package it.unive.aiutovicino.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.databinding.FragmentConvalidaBinding;
import it.unive.aiutovicino.databinding.FragmentConvalidaUsersBinding;


public class ConvalidaUsersFragment extends Fragment {
    private FragmentConvalidaUsersBinding binding;

    public static ConvalidaUsersFragment newInstance() {
        ConvalidaUsersFragment fragment = new ConvalidaUsersFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConvalidaUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}