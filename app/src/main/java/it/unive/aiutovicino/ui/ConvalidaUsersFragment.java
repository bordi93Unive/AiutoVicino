package it.unive.aiutovicino.ui;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentConvalidaBinding;
import it.unive.aiutovicino.databinding.FragmentConvalidaUsersBinding;
import it.unive.aiutovicino.model.UserModel;


public class ConvalidaUsersFragment extends Fragment {
    private FragmentConvalidaUsersBinding binding;

    public static ConvalidaUsersFragment newInstance() {
        ConvalidaUsersFragment fragment = new ConvalidaUsersFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConvalidaUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<UserModel> users;
        try {
            users = (List<UserModel>)new Connection().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class Connection extends AsyncTask {
        @Override
        protected Object doInBackground(Object... arg0){
            return UserController.getNotApprovedUsers();
        }
    }
}