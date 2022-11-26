package it.unive.aiutovicino.ui.classifica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.AnnunciAdapter;
import it.unive.aiutovicino.adapter.ClassificaAdapter;
import it.unive.aiutovicino.controller.AnnuncioController;
import it.unive.aiutovicino.controller.RankingController;
import it.unive.aiutovicino.databinding.FragmentClassificaBinding;
import it.unive.aiutovicino.model.AnnuncioModel;
import it.unive.aiutovicino.model.RankingModel;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class ClassificaFragment extends Fragment {

    private FragmentClassificaBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ClassificaViewModel classificaViewModel = new ViewModelProvider(this).get(ClassificaViewModel.class);

        binding = FragmentClassificaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RankingModel[] rankings = RankingController.getAllRankings();

        ListView listRankings = (ListView) binding.listClassifica;
        ClassificaAdapter classificaAdapter = new ClassificaAdapter(root.getContext(), rankings);
        listRankings.setAdapter(classificaAdapter);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}