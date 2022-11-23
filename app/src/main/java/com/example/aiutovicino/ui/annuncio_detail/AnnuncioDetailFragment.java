package com.example.aiutovicino.ui.annuncio_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aiutovicino.R;
import com.example.aiutovicino.controller.AnnuncioController;
import com.example.aiutovicino.databinding.FragmentAnnuncioDetailBinding;
import com.example.aiutovicino.model.AnnuncioModel;

public class AnnuncioDetailFragment extends Fragment {

    private FragmentAnnuncioDetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnnuncioDetailViewModel annuncioDetailViewModel = new ViewModelProvider(this).get(AnnuncioDetailViewModel.class);

        binding = FragmentAnnuncioDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle b = this.getArguments();

        AnnuncioModel annuncio = AnnuncioController.getAnnouncment((int)b.get("id"));

        if(annuncio == null){
            //torna indietro
        }
        else{
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
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}