package it.unive.aiutovicino.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.LoginActivity;
import it.unive.aiutovicino.databinding.FragmentLogoutBinding;


public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        General.resetSharedPreferences(this.getActivity(), binding.getRoot().getContext().MODE_PRIVATE);
        startActivity(new Intent(getActivity(), LoginActivity.class));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}