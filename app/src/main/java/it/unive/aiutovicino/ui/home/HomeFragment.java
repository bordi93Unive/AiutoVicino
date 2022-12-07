package it.unive.aiutovicino.ui.home;

import android.app.Activity;
import android.content.SharedPreferences;
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

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.AnnunciAdapter;
import it.unive.aiutovicino.controller.AnnouncementController;
import it.unive.aiutovicino.databinding.FragmentHomeBinding;
import it.unive.aiutovicino.model.AnnouncementModel;
import it.unive.aiutovicino.model.UserModel;

import com.google.gson.Gson;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListView listAnnouncements;
    private ProgressBar progressSpinner;
    private AnnunciAdapter announcementAdapter;
    private List<AnnouncementModel> announcements;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        General.getUserBySharedPreferences(this.getActivity(), binding.getRoot().getContext().MODE_PRIVATE);

        progressSpinner = binding.progressBarHome;

        listAnnouncements = (ListView) binding.listHome;
        announcementAdapter = new AnnunciAdapter(root.getContext());

        /*searchView = root.findViewById(R.id.action_search);
        searchView.setVisibility(View.VISIBLE);*/

        new Connection().execute();

        listAnnouncements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Gson gson = new Gson();
                String json = gson.toJson(announcements.get(i));

                Bundle b = new Bundle();
                b.putString("announcement", json);
                b.putString("type", "announcement");
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
            return AnnouncementController.getAllAnnouncements();
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                announcements = (List<AnnouncementModel>)result;
                announcementAdapter.setAnnunci(announcements);
                listAnnouncements.setAdapter(announcementAdapter);
            }
            progressSpinner.setVisibility(View.GONE);

        }
    }
}

