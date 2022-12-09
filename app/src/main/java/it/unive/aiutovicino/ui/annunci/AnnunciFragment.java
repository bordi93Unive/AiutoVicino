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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.gson.Gson;

import java.util.List;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.AnnunciAdapter;
import it.unive.aiutovicino.controller.AnnouncementController;
import it.unive.aiutovicino.databinding.FragmentAnnunciBinding;
import it.unive.aiutovicino.model.AnnouncementModel;
import it.unive.aiutovicino.ui.SearchViewModel;

public class AnnunciFragment extends Fragment {

    private FragmentAnnunciBinding binding;
    ListView listAnnunci;
    AnnunciAdapter announcementAdapter;
    List<AnnouncementModel> annunci;
    ProgressBar progressSpinner;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel viewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
        binding = FragmentAnnunciBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        progressSpinner = binding.progressBarMyAnnunci;
        /*searchView = root.findViewById(R.id.action_search);
        searchView.setVisibility(View.VISIBLE);*/
        listAnnunci = (ListView) binding.listMyAnnunci;
        announcementAdapter = new AnnunciAdapter(root.getContext());



        new Connection().execute();

        viewModel.getFilter().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                announcementAdapter.getFilter().filter(s);
            }
        });

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
                announcementAdapter.setAnnunci(annunci);
                listAnnunci.setAdapter(announcementAdapter);
            }
            progressSpinner.setVisibility(View.GONE);

        }
    }


}