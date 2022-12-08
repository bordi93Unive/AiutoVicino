package it.unive.aiutovicino.ui.annuncio_detail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.AnnouncementController;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnnuncioDetailViewModel annuncioDetailViewModel = new ViewModelProvider(this).get(AnnuncioDetailViewModel.class);

        binding = FragmentAnnuncioDetailBinding.inflate(inflater, container, false);
        root = binding.getRoot();



        Bundle b = this.getArguments();
        Gson gson = new Gson();
        String json = (String)b.get("announcement");
        String type = (String)b.get("type");



        if(json != null && !json.equals("")) {
            annuncio = gson.fromJson(json, AnnouncementModel.class);
        }

        if(annuncio == null){
            Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.action_annuncioDetailFragment_to_nav_home);
        }
        else {
            switch(type){
                case "announcement":
                    binding.buttonApplicati.setVisibility(View.VISIBLE);
                    binding.textNApplicazioni.setVisibility(View.VISIBLE);
                    usersApplyed = annuncio.getUserApplyed();
                    if( usersApplyed != null ) {
                        binding.textNApplicazioni.setText(usersApplyed.size() + " su " + annuncio.getParticipantsNumber());
                    }
                    break;
                case "my_announcement":

                    binding.textApplicazioni.setVisibility(View.VISIBLE);
                    //box con lista utenti applicati annuncio.getUserApplyed();
                    usersApplyed = annuncio.getUserApplyed();
                    if( usersApplyed != null ) {
                        StringBuffer textApplyed = new StringBuffer();
                        for (UserModel user : usersApplyed) {
                            //popolo la textbox con il nome di chi si è applicato
                            textApplyed.append(user.getName() + " " + user.getSurname() + "\n");
                        }
                        binding.textApplicazioni.setText(textApplyed);
                        binding.buttonConfermaAttivita.setVisibility(View.VISIBLE);
                    }


                    break;
            }

            binding.textAnnuncioTitle.setText(annuncio.getTitle());
            switch(annuncio.getIdCategory()) {
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

            binding.textDescrizione.setMovementMethod(new ScrollingMovementMethod()); //per rendere la textView scrollabile
            binding.textDescrizione.setText(annuncio.getDescription());
        }
            /** @todo se premi il bottone poi non posso più andare su Home */
        binding.buttonApplicati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Connection().execute();

            }
        });

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
        //Navigation.findNavController(view).navigate(R.id.action_annuncioDetailFragment_to_nav_applicazioni);
    }

    private class Connection extends AsyncTask {

        @Override
        protected void onPreExecute() {
            //progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0){

            return AnnouncementController.apply(annuncio.getId());
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result == null || !((Boolean) result)){
                applyError();
            } else {
                applyOk();
                //reindirizzo al fragment Miei Annunci
                //Navigation.findNavController(getView()).navigate(R.id.action_navAnnuncioCrea_to_navAnnunci);
            }
            //progressSpinner.setVisibility(View.GONE);
        }
    }
}