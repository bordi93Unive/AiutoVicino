package it.unive.aiutovicino.ui.annuncio_crea;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.AnnuncioController;
import it.unive.aiutovicino.databinding.FragmentAnnuncioCreaBinding;
import it.unive.aiutovicino.databinding.FragmentAnnuncioDetailBinding;
import it.unive.aiutovicino.model.AnnuncioModel;
import it.unive.aiutovicino.ui.annuncio_detail.AnnuncioDetailViewModel;

public class AnnuncioCreaFragment extends Fragment {

    private FragmentAnnuncioCreaBinding binding;

    String[] item ={"Ugo","Ugata","Ugato"};

    AutoCompleteTextView autoCompleteTextView;
    Button button;
    ArrayAdapter<String> adapterItems;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnnuncioCreaViewModel annuncioCreaViewModel = new ViewModelProvider(this).get(AnnuncioCreaViewModel.class);

        binding = FragmentAnnuncioCreaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        autoCompleteTextView = binding.autoCompleteTxt;
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_item_annuncio_crea,item);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
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