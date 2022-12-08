package it.unive.aiutovicino.ui;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.AnnunciAdapter;
import it.unive.aiutovicino.adapter.UserAdapter;
import it.unive.aiutovicino.controller.AnnouncementController;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentConvalidaBinding;
import it.unive.aiutovicino.databinding.FragmentConvalidaUsersBinding;
import it.unive.aiutovicino.model.AnnouncementModel;
import it.unive.aiutovicino.model.UserModel;
import it.unive.aiutovicino.ui.home.HomeFragment;


public class ConvalidaUsersFragment extends Fragment {
    private FragmentConvalidaUsersBinding binding;
    private ListView listUsers;
    private ProgressBar progressSpinner;
    private UserAdapter userAdapter;
    private List<UserModel> users;

    public static ConvalidaUsersFragment newInstance() {
        ConvalidaUsersFragment fragment = new ConvalidaUsersFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConvalidaUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressSpinner = binding.progressBarConvalidaUsers;

        listUsers =  binding.listConvalidaUsers;
        userAdapter = new UserAdapter(root.getContext());

        new Connection().execute();

        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class Connection extends AsyncTask {
        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0)
        {
            return UserController.getNotApprovedUsers();
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                users = (List<UserModel>)result;
                userAdapter.setUsers(users);
                listUsers.setAdapter(userAdapter);
            }
            progressSpinner.setVisibility(View.GONE);

        }
    }
}