package it.unive.aiutovicino.ui.fragment;

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
import it.unive.aiutovicino.databinding.FragmentApplicazioniBinding;
import it.unive.aiutovicino.model.AnnouncementModel;
import it.unive.aiutovicino.ui.viewmodel.SearchViewModel;

public class ApplicazioniFragment extends Fragment {

    private FragmentApplicazioniBinding binding;
    ListView listApplications;
    AnnunciAdapter announcementAdapter;
    List<AnnouncementModel> announcements;
    ProgressBar progressSpinner;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel viewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        binding = FragmentApplicazioniBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        progressSpinner = binding.progressBarApplicazioni;
        listApplications = binding.listApplications;
        announcementAdapter = new AnnunciAdapter(root.getContext());

        new Connection().execute();

        viewModel.getFilter().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!(announcementAdapter.getCount() == 0 && s.equals(""))){
                    announcementAdapter.getFilter().filter(s);
                }
            }
        });

        listApplications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Gson gson = new Gson();
                String json = gson.toJson(announcements.get(i));

                Bundle b = new Bundle();
                b.putString("announcement", json);
                b.putString("type", "my_application");
                Navigation.findNavController(view).navigate(R.id.action_nav_applicazioni_to_annuncioDetailFragment, b);
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
            return AnnouncementController.getAnnouncementsAppliedByUserId();
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                announcements = (List<AnnouncementModel>)result;
                announcementAdapter.setAnnunci(announcements);
                listApplications.setAdapter(announcementAdapter);
            }
            progressSpinner.setVisibility(View.GONE);

        }
    }
}