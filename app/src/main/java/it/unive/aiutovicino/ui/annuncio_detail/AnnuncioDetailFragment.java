package it.unive.aiutovicino.ui.annuncio_detail;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import it.unive.aiutovicino.MainActivity;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.AnnuncioController;
import it.unive.aiutovicino.databinding.FragmentAnnuncioDetailBinding;
import it.unive.aiutovicino.model.AnnuncioModel;
import com.google.android.material.snackbar.Snackbar;

public class AnnuncioDetailFragment extends Fragment {

    private FragmentAnnuncioDetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnnuncioDetailViewModel annuncioDetailViewModel = new ViewModelProvider(this).get(AnnuncioDetailViewModel.class);

        binding = FragmentAnnuncioDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle b = this.getArguments();

        AnnuncioModel annuncio = AnnuncioController.getAnnouncment((int)b.get("id"));

        if(annuncio == null){
            Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.action_annuncioDetailFragment_to_nav_home);
        }
        else {
            binding.textAnnuncioTitle.setText(annuncio.title);
            switch(annuncio.id_category) {
                case 1:
                    binding.annuncioIcon.setImageResource(R.drawable.category_anziani);
                    break;
                case 2:
                    binding.annuncioIcon.setImageResource(R.drawable.category_bambini);
                    break;
                case 3:
                    binding.annuncioIcon.setImageResource(R.drawable.category_cani);
                    break;
            }
            binding.textDate.setText(annuncio.date);
            binding.textTime.setText(annuncio.time);
            binding.textLocation.setText(annuncio.place);
            binding.textPartecipanti.setText((String.valueOf(annuncio.partecipants_number)));

            binding.textDescrizione.setMovementMethod(new ScrollingMovementMethod()); //per rendere la textView scrollabile
            binding.textDescrizione.setText(annuncio.description);
        }
            /**TODO se premi il bottone poi non posso più andare su Home */
        binding.buttonApplicati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ti sei applicato con successo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Navigation.findNavController(view).navigate(R.id.action_annuncioDetailFragment_to_nav_applicazioni);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}