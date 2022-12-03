package it.unive.aiutovicino.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;
    ListView listAnnunci;
    AnnunciAdapter annunciAdapter;
    List<AnnuncioModel> annunci;
    ProgressBar progressSpinner;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressSpinner = binding.progressBarHome;

        listAnnunci = (ListView) binding.listHome;
        annunciAdapter = new AnnunciAdapter(root.getContext());
        new Connection().execute();

        listAnnunci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Gson gson = new Gson();
                String json = gson.toJson(annunci.get(i));

                Bundle b = new Bundle();
                b.putString("annuncio", json);
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

    private class Connection extends AsyncTask {
        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0)
        {
            return AnnuncioController.getAllAnnouncments();
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                annunci = (List<AnnuncioModel>)result;
                annunciAdapter.setAnnunci(annunci);
                listAnnunci.setAdapter(annunciAdapter);
            }
            progressSpinner.setVisibility(View.GONE);

        }
    }
}

