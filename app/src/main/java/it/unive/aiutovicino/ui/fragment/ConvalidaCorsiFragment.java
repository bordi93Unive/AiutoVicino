package it.unive.aiutovicino.ui.fragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.gson.Gson;

import java.util.List;
import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.AnnunciAdapter;
import it.unive.aiutovicino.controller.AnnouncementController;
import it.unive.aiutovicino.databinding.FragmentConvalidaCorsiBinding;
import it.unive.aiutovicino.model.AnnouncementModel;


public class ConvalidaCorsiFragment extends Fragment {
    private FragmentConvalidaCorsiBinding binding;
    private ListView listCorsi;
    private ProgressBar progressSpinner;
    private AnnunciAdapter announcementAdapter;
    private List<AnnouncementModel> announcements;
    private View rootView;

    public static ConvalidaCorsiFragment newInstance() {
        ConvalidaCorsiFragment fragment = new ConvalidaCorsiFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConvalidaCorsiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(General.checkTokenExpiration()){
            NavController n = findNavController(this);
            n.navigate(R.id.action_nav_convalida_corsi_to_nav_logout);
        }
        else {
            General.setSearchViewInvisible();
            progressSpinner = binding.progressBarConvalidaCorsi;

            listCorsi = binding.listConvalidaCorsi;
            announcementAdapter = new AnnunciAdapter(root.getContext());

            new Connection().execute();

            listCorsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Gson gson = new Gson();
                    String json = gson.toJson(announcements.get(i));

                    Bundle b = new Bundle();
                    b.putString("announcement", json);
                    Navigation.findNavController(view).navigate(R.id.action_nav_convalida_to_convalidaCorsoDetailFragment, b);
                }
            });
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        announcementAdapter = null;
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
            return AnnouncementController.getCoursesToApprove();
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                announcements = (List<AnnouncementModel>)result;
                announcementAdapter.setAnnunci(announcements);
                listCorsi.setAdapter(announcementAdapter);
            }
            progressSpinner.setVisibility(View.GONE);

        }
    }
}