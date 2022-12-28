package it.unive.aiutovicino.ui.fragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.AnnouncementController;
import it.unive.aiutovicino.databinding.FragmentConvalidaCorsoDetailBinding;
import it.unive.aiutovicino.model.AnnouncementModel;
import it.unive.aiutovicino.model.UserModel;

public class ConvalidaCorsoDetailFragment extends Fragment {

    private FragmentConvalidaCorsoDetailBinding binding;
    View root;
    AnnouncementModel annuncio = null;
    List<UserModel> usersApplyed;
    StringBuffer textApplyed;
    private ProgressBar progressSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConvalidaCorsoDetailBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        if(General.checkTokenExpiration()){
            NavController n = findNavController(this);
            n.navigate(R.id.action_nav_convalida_corso_detail_to_nav_logout);
        }
        else {

            Bundle b = this.getArguments();
            Gson gson = new Gson();
            String json = (String) b.get("announcement");
            String type = (String) b.get("type");


            if (json != null && !json.equals("")) {
                annuncio = gson.fromJson(json, AnnouncementModel.class);
            }

            if (annuncio == null) {
                Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.action_annuncioDetailFragment_to_nav_home);
            } else {
                binding.textAnnuncioTitle.setText(annuncio.getTitle());
                switch (annuncio.getIdCategory()) {
                    case "1":
                        binding.annuncioIcon.setImageResource(R.drawable.category_anziani);
                        break;
                    case "2":
                        binding.annuncioIcon.setImageResource(R.drawable.category_bambini);
                        break;
                    case "3":
                        binding.annuncioIcon.setImageResource(R.drawable.category_cani);
                        break;
                }
                binding.textDate.setText(annuncio.getDate());
                binding.textTime.setText(annuncio.getHours());
                binding.textLocation.setText(annuncio.getPlace());
                binding.textPartecipanti.setText((String.valueOf(annuncio.getParticipantsNumber())));
                binding.textCreatoreConvalida.setText(annuncio.getCreator());
                binding.textCoin.setText((String.valueOf(annuncio.getCoins())));
                binding.textDescrizione.setMovementMethod(new ScrollingMovementMethod()); //per rendere la textView scrollabile
                binding.textDescrizione.setText(annuncio.getDescription());
                progressSpinner = binding.progressBarConvalidaCorsoDetail;
            }

            binding.buttonApprovaCorso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    annuncio.setCoins(Integer.parseInt(binding.textCoin.getText().toString().trim()));
                    new Connection().execute();

                }
            });

            binding.buttonEliminaCorso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new ConnectionDelete().execute();

                }
            });
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void applyError(){
        Snackbar.make(root, "Errore durante l'applicazione", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void applyOk(){
        Snackbar.make(root, "Corso validato con successo", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Navigation.findNavController(root).navigate(R.id.action_convalidaCorsoDetailFragment_to_nav_convalida);
    }

    private class Connection extends AsyncTask {

        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
            binding.buttonApprovaCorso.setEnabled(false);
            binding.buttonEliminaCorso.setEnabled(false);
        }

        @Override
        protected Object doInBackground(Object... arg0){

            return AnnouncementController.approveCourse(annuncio);
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result == null || !((Boolean) result)){
                applyError();
            } else {
                applyOk();
            }
            progressSpinner.setVisibility(View.GONE);
            binding.buttonApprovaCorso.setEnabled(true);
            binding.buttonEliminaCorso.setEnabled(true);
        }
    }

    private void deleteError(){
        Snackbar.make(root, "Errore durante l'eliminazione", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void deleteOk(){
        Snackbar.make(root, "Eliminato con successo", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Navigation.findNavController(root).navigate(R.id.action_convalidaCorsoDetailFragment_to_nav_convalida);
    }

    private class ConnectionDelete extends AsyncTask {

        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
            binding.buttonApprovaCorso.setEnabled(false);
            binding.buttonEliminaCorso.setEnabled(false);
        }

        @Override
        protected Object doInBackground(Object... arg0){

            return AnnouncementController.delete(annuncio.getId());
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result == null || !((Boolean) result)){
                deleteError();
            } else {
                deleteOk();
            }
            progressSpinner.setVisibility(View.GONE);
            binding.buttonApprovaCorso.setEnabled(true);
            binding.buttonEliminaCorso.setEnabled(true);
        }
    }
}