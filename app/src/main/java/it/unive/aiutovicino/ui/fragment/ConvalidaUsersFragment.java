package it.unive.aiutovicino.ui.fragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.adapter.UserAdapter;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentConvalidaBinding;
import it.unive.aiutovicino.databinding.FragmentConvalidaUsersBinding;
import it.unive.aiutovicino.model.UserModel;


public class ConvalidaUsersFragment extends Fragment {
    private FragmentConvalidaUsersBinding binding;
    private ListView listUsers;
    private ProgressBar progressSpinner;
    private UserAdapter userAdapter;
    private List<UserModel> users;
    private View rootView;

    public static ConvalidaUsersFragment newInstance() {
        ConvalidaUsersFragment fragment = new ConvalidaUsersFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConvalidaUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        General.setSearchViewInvisible();

        if(General.checkTokenExpiration()){
            NavController n = findNavController(this);
            n.navigate(R.id.action_nav_convalida_users_to_nav_logout);
        }
        else {
            progressSpinner = binding.progressBarConvalidaUsers;
            listUsers = binding.listConvalidaUsers;
            userAdapter = new UserAdapter(root.getContext());

            new Connection().execute();

            listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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

    private class Connection extends AsyncTask {
        @Override
        protected void onPreExecute() {
            progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0)
        {
            return UserController.getNotApprovedUsers(General.user.getId());
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