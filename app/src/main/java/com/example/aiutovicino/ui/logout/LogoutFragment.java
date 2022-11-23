package com.example.aiutovicino.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aiutovicino.LoginActivity;
import com.example.aiutovicino.MainActivity;
import com.example.aiutovicino.databinding.FragmentHomeBinding;
import com.example.aiutovicino.databinding.FragmentLogoutBinding;
import com.example.aiutovicino.ui.home.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;

public class LogoutFragment extends Fragment {

private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogoutViewModel logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        startActivity(new Intent(getActivity(), LoginActivity.class));

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}