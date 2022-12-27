package it.unive.aiutovicino.ui.fragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.AnnouncementController;
import it.unive.aiutovicino.controller.RankingController;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentAnnuncioDetailBinding;
import it.unive.aiutovicino.model.AnnouncementModel;
import it.unive.aiutovicino.model.UserModel;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class AnnuncioDetailFragment extends Fragment {

    private FragmentAnnuncioDetailBinding binding;
    View root;
    AnnouncementModel annuncio = null;
    List<UserModel> usersApplyed;
    StringBuffer textApplyed;
    private ProgressBar progressSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAnnuncioDetailBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        if(General.checkTokenExpiration()){
            NavController n = findNavController(this);
            n.navigate(R.id.action_nav_annuncio_detail_to_nav_logout);
        }
        else {

            General.setSearchViewInvisible();

            Bundle b = this.getArguments();
            Gson gson = new Gson();
            String json = (String) b.get("announcement");
            String type = (String) b.get("type");

            progressSpinner = binding.progressBarDetail;

            if (json != null && !json.equals("")) {
                annuncio = gson.fromJson(json, AnnouncementModel.class);
            }

            if (annuncio == null) {
                Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.action_annuncioDetailFragment_to_nav_home);
            } else {
                usersApplyed = annuncio.getUserApplied();

                switch (type) {
                    case "announcement":
                        binding.buttonApplicati.setVisibility(View.VISIBLE);
                        binding.textNApplicazioni.setVisibility(View.VISIBLE);
                        if (usersApplyed != null) {
                            binding.textNApplicazioni.setText(usersApplyed.size() + " su " + annuncio.getParticipantsNumber());
                        }
                        break;
                    case "my_application":
                        binding.textNApplicazioni.setVisibility(View.VISIBLE);
                        if (usersApplyed != null) {
                            binding.textNApplicazioni.setText(usersApplyed.size() + " su " + annuncio.getParticipantsNumber());
                        }
                        break;
                    case "my_announcement":
                        binding.textApplicazioni.setVisibility(View.VISIBLE);
                        //box con lista utenti applicati annuncio.getUserApplyed();
                        usersApplyed = annuncio.getUserApplied();
                        if (usersApplyed == null || usersApplyed.isEmpty()) {
                            //mostro il bottone cancello annuncio solo se nessuno si è applicato
                            binding.buttonEliminaAnnuncio.setVisibility(View.VISIBLE);
                            binding.buttonEliminaAnnuncio.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new ConnectionDelete().execute();
                                }
                            });
                        } else {
                            StringBuffer textApplyed = new StringBuffer();
                            for (UserModel user : usersApplyed) {
                                //popolo la textbox con il nome di chi si è applicato
                                textApplyed.append(user.getNickname() + "\n");
                            }
                            binding.textApplicazioni.setText(textApplyed);
                            //mostro il bottone di conferma attività solo se tutti si sono applicati
                            if (usersApplyed.size() == annuncio.getParticipantsNumber() && annuncio.getStatus().equals("open")) {
                                binding.buttonConfermaAttivita.setVisibility(View.VISIBLE);
                                binding.buttonConfermaAttivita.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new ConnectionConfirm().execute();
                                    }
                                });
                            }
                        }
                        break;
                }

                binding.textAnnuncioTitle.setText(annuncio.getTitle());
                switch (annuncio.getIdCategory()) {
                    case "1":
                        binding.annuncioIcon.setImageResource(R.drawable.category_corsi);
                        break;
                    case "2":
                        binding.annuncioIcon.setImageResource(R.drawable.category_bambini);
                        break;
                    case "3":
                        binding.annuncioIcon.setImageResource(R.drawable.category_anziani);
                        break;
                    case "4":
                        binding.annuncioIcon.setImageResource(R.drawable.category_cani);
                        break;
                    case "5":
                        binding.annuncioIcon.setImageResource(R.drawable.category_trasporti);
                        break;

                }
                binding.textDate.setText(annuncio.getDate());
                binding.textTime.setText(annuncio.getHours());
                binding.textLocation.setText(annuncio.getPlace());
                binding.textPartecipanti.setText((String.valueOf(annuncio.getParticipantsNumber())));
                binding.textCreatore.setText(annuncio.getCreator());
                binding.textCoin.setText(String.valueOf(annuncio.getCoins()));
                binding.textDescrizione.setText(annuncio.getDescription());
                binding.textDescrizione.setMovementMethod(new ScrollingMovementMethod()); //per rendere la textView scrollabile
                binding.textDescrizione.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        binding.textDescrizione.getParent().requestDisallowInterceptTouchEvent(true);

                        return false;
                    }
                });
            }

            binding.buttonApplicati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Connection().execute();

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
        Snackbar.make(root, "Applicato con successo", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Navigation.findNavController(root).navigate(R.id.action_annuncioDetailFragment_to_nav_applicazioni);
    }

    private class Connection extends AsyncTask {

        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0){
            boolean result = AnnouncementController.apply(annuncio.getId());
            if(result){
                RankingController.getUserScore();
            }
            return result;
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
        }
    }

    private void deleteError(){
        Snackbar.make(root, "Errore durante l'eliminazione", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void deleteOk(){
        Snackbar.make(root, "Eliminato con successo", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Navigation.findNavController(root).navigate(R.id.action_annuncioDetailFragment_to_nav_home);
    }

    private class ConnectionDelete extends AsyncTask {

        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0){
            boolean result = AnnouncementController.delete(annuncio.getId());
            if(result){
                RankingController.getUserScore();
            }
            return result;
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
        }
    }

    private void confirmError(){
        Snackbar.make(root, "Errore durante la conferma dell'attività", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void confirmOk(){
        Snackbar.make(root, "Attività confermata con successo", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Navigation.findNavController(root).navigate(R.id.action_annuncioDetailFragment_to_nav_annunci);
    }


    private class ConnectionConfirm extends AsyncTask {

        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0){
            boolean result = AnnouncementController.confirm(annuncio.getId());
            if(result){
                RankingController.getUserScore();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result == null || !((Boolean) result)){
                confirmError();
            } else {
                confirmOk();
            }
            progressSpinner.setVisibility(View.GONE);
        }
    }
}