package it.unive.aiutovicino.ui.fragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.viewpager.widget.ViewPager;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.ConvalidaPagerAdapter;
import it.unive.aiutovicino.databinding.FragmentConvalidaBinding;

public class ConvalidaFragment extends Fragment {

    private FragmentConvalidaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConvalidaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setHasOptionsMenu(true);

        if(General.checkTokenExpiration()){
            NavController n = findNavController(this);
            n.navigate(R.id.action_nav_convalida_to_nav_logout);
        }
        else {

            ConvalidaPagerAdapter sectionsPagerAdapter = new ConvalidaPagerAdapter(this.getContext(), getChildFragmentManager());
            ViewPager viewPager = binding.viewPager;
            viewPager.setAdapter(sectionsPagerAdapter);
            General.setVisibleTabs(this.getActivity(), viewPager);
        }
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
        General.setNotVisibleTabs(this.getActivity());
    }
}