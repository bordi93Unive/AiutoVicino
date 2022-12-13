package it.unive.aiutovicino.ui.classifica;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.RankingAdapter;
import it.unive.aiutovicino.controller.RankingController;
import it.unive.aiutovicino.databinding.FragmentClassificaBinding;
import it.unive.aiutovicino.model.RankingModel;

public class ClassificaFragment extends Fragment {

    private FragmentClassificaBinding binding;
    private ProgressBar progressSpinner;
    private ListView listRankings;
    private RankingAdapter rankingAdapter;
    private List<RankingModel> rankings;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClassificaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setHasOptionsMenu(true);

        progressSpinner = binding.progressBarClassifica;
        listRankings = binding.listClassifica;
        rankingAdapter = new RankingAdapter(root.getContext());

        new Connection().execute();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_search);
        if(item!=null)
            item.setVisible(false);
    }

    private class Connection extends AsyncTask {
        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0)
        {
            return RankingController.getAllRankings();
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                rankings = (List<RankingModel>)result;
                rankingAdapter.setRankings(rankings);
                listRankings.setAdapter(rankingAdapter);
            }
            progressSpinner.setVisibility(View.GONE);

        }
    }
}