package it.unive.aiutovicino.ui.convalida;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.ConvalidaPagerAdapter;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentConvalidaBinding;

import com.google.android.material.tabs.TabLayout;

public class ConvalidaFragment extends Fragment {

    private FragmentConvalidaBinding binding;

    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConvalidaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ConvalidaPagerAdapter sectionsPagerAdapter = new ConvalidaPagerAdapter(this.getContext(), this.getActivity().getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        General.setVisibleTabs(this.getActivity(), viewPager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        General.setNotVisibleTabs(this.getActivity());
        binding = null;
    }
}