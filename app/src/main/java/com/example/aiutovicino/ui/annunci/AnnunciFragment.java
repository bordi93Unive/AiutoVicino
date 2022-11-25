package com.example.aiutovicino.ui.annunci;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.aiutovicino.R;
import com.example.aiutovicino.adapter.AnnunciAdapter;
import com.example.aiutovicino.controller.AnnuncioController;
import com.example.aiutovicino.databinding.FragmentAnnunciBinding;
import com.example.aiutovicino.model.AnnuncioModel;

public class AnnunciFragment extends Fragment {

private FragmentAnnunciBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnnunciViewModel annunciViewModel = new ViewModelProvider(this).get(AnnunciViewModel.class);

        binding = FragmentAnnunciBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AnnuncioModel[] annunci = AnnuncioController.getAllMyAnnouncments();

        ListView listAnnunci = (ListView) binding.listMyAnnunci;
        AnnunciAdapter annunciAdapter = new AnnunciAdapter(root.getContext(), annunci);
        listAnnunci.setAdapter(annunciAdapter);

        listAnnunci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle b = new Bundle();
                b.putInt("id", annunci[i].id);
                //Navigation.findNavController(view).navigate(R.id.action_nav_home_to_annuncioDetailFragment, b);
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