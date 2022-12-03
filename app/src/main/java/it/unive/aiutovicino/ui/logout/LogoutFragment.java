package it.unive.aiutovicino.ui.logout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.LoginActivity;
import it.unive.aiutovicino.databinding.FragmentLogoutBinding;
import it.unive.aiutovicino.ui.login.LoginFragment;


public class LogoutFragment extends Fragment {

private FragmentLogoutBinding binding;
    SharedPreferences sharedpreferences;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogoutViewModel logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedpreferences = this.getActivity().getSharedPreferences(General.SHARED_PREFS, root.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();

        startActivity(new Intent(getActivity(), LoginActivity.class));

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}