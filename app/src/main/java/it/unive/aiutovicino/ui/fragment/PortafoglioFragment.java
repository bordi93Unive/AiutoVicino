package it.unive.aiutovicino.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.concurrent.ExecutionException;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.RankingController;
import it.unive.aiutovicino.databinding.FragmentPortafoglioBinding;

public class PortafoglioFragment extends Fragment {

    private FragmentPortafoglioBinding binding;
    private View root;
    private TextView textPortafoglio;
    private TextView textScore;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPortafoglioBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        setHasOptionsMenu(true);

        try {
           new Connection().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        textPortafoglio = binding.textPortafoglio;
        textScore = binding.textScore;

        textPortafoglio.setText("Ciao " + General.user.getName() + " " + General.user.getSurname() + "!");
        textScore.setText(String.valueOf(General.user.getScore()));
        new Connection().execute();

        binding.buttonVisualizzaClassifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_portafoglio_to_classificaFragment);
            }
        });

        return root;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_search);
        if(item!=null)
            item.setVisible(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class Connection extends AsyncTask {
        @Override
        protected Object doInBackground(Object... arg0){
            RankingController.getUserScore();
            return General.user.getScore();
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                int score = (Integer)result;
                textScore.setText(String.valueOf(score));
            }

        }
    }

}