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

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.AnnunciAdapter;
import it.unive.aiutovicino.controller.AnnuncioController;
import it.unive.aiutovicino.databinding.FragmentAnnunciBinding;
import it.unive.aiutovicino.model.AnnuncioModel;

public class AnnunciFragment extends Fragment {

    private FragmentAnnunciBinding binding;
    ListView listAnnunci;
    AnnunciAdapter annunciAdapter;
    List<AnnuncioModel> annunci;
    ProgressBar progressSpinner;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnnunciViewModel annunciViewModel = new ViewModelProvider(this).get(AnnunciViewModel.class);

        binding = FragmentAnnunciBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        progressSpinner = binding.progressBarMyAnnunci;

        /**List<AnnuncioModel> annunci = AnnuncioController.getAllMyAnnouncements();**/

        ListView listAnnunci = (ListView) binding.listMyAnnunci;
        AnnunciAdapter annunciAdapter = new AnnunciAdapter(root.getContext(), annunci);
        listAnnunci.setAdapter(annunciAdapter);

        listAnnunci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle b = new Bundle();
                b.putString("id", annunci.get(i).id);
                //Navigation.findNavController(view).navigate(R.id.action_nav_home_to_annuncioDetailFragment, b);
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
    private void createError(){
        Snackbar.make(root, "Errore caricamento miei annunci", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void createOk(){
        Snackbar.make(root, "Miei Annunci caricati", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private class MieiAnnunci extends AsyncTask {

        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0)
        {
            return AnnuncioController.getAllMyAnnouncements();
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