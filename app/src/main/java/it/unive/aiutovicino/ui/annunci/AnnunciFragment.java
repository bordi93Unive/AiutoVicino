package it.unive.aiutovicino.ui.annunci;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.gson.Gson;

import java.util.List;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.AnnunciAdapter;
import it.unive.aiutovicino.controller.AnnouncementController;
import it.unive.aiutovicino.databinding.FragmentAnnunciBinding;
import it.unive.aiutovicino.model.AnnouncementModel;

public class AnnunciFragment extends Fragment {

    private FragmentAnnunciBinding binding;
    ListView listAnnunci;
    AnnunciAdapter annunciAdapter;
    List<AnnouncementModel> annunci;
    ProgressBar progressSpinner;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnnunciViewModel annunciViewModel = new ViewModelProvider(this).get(AnnunciViewModel.class);

        binding = FragmentAnnunciBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        progressSpinner = binding.progressBarMyAnnunci;
        /*searchView = root.findViewById(R.id.action_search);
        searchView.setVisibility(View.VISIBLE);*/
        listAnnunci = (ListView) binding.listMyAnnunci;
        annunciAdapter = new AnnunciAdapter(root.getContext());

        new Connection().execute();

        listAnnunci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Gson gson = new Gson();
                String json = gson.toJson(annunci.get(i));

                Bundle b = new Bundle();
                b.putString("announcement", json);
                b.putString("type", "my_announcement");
                Navigation.findNavController(view).navigate(R.id.action_nav_annunci_to_annuncioDetailFragment, b);
            }
        });

        binding.buttonCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_annunci_to_nav_annuncio_crea);
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
            return AnnouncementController.getAllMyAnnouncements();
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                annunci = (List<AnnouncementModel>)result;
                annunciAdapter.setAnnunci(annunci);
                listAnnunci.setAdapter(annunciAdapter);
            }
            progressSpinner.setVisibility(View.GONE);

        }
    }


}