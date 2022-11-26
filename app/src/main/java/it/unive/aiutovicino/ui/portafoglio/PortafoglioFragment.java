package it.unive.aiutovicino.ui.portafoglio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.databinding.FragmentPortafoglioBinding;

public class PortafoglioFragment extends Fragment {

private FragmentPortafoglioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PortafoglioViewModel portafoglioViewModel = new ViewModelProvider(this).get(PortafoglioViewModel.class);

        binding = FragmentPortafoglioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonVisualizzaClassifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_portafoglio_to_classificaFragment);
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