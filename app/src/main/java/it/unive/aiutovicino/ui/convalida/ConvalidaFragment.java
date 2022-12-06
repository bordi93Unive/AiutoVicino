package it.unive.aiutovicino.ui.convalida;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.databinding.FragmentConvalidaBinding;
import it.unive.aiutovicino.databinding.FragmentHomeBinding;
import it.unive.aiutovicino.ui.home.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ConvalidaFragment extends Fragment {

private FragmentConvalidaBinding binding;

    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        ConvalidaViewModel convalidaViewModel = new ViewModelProvider(this).get(ConvalidaViewModel.class);

        binding = FragmentConvalidaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*searchView = root.findViewById(R.id.action_search);
        searchView.setVisibility(View.GONE);*/

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}