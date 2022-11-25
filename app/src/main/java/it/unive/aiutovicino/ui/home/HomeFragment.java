package it.unive.aiutovicino.ui.home;

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
import it.unive.aiutovicino.controller.AnnuncioController;
import it.unive.aiutovicino.databinding.FragmentHomeBinding;
import it.unive.aiutovicino.model.AnnuncioModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AnnuncioModel[] annunci = AnnuncioController.getAllAnnouncments();

        ListView listAnnunci = (ListView) binding.listHome;
        AnnunciAdapter annunciAdapter = new AnnunciAdapter(root.getContext(), annunci);
        listAnnunci.setAdapter(annunciAdapter);

        listAnnunci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle b = new Bundle();
                b.putInt("id", annunci[i].id);
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_annuncioDetailFragment, b);
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