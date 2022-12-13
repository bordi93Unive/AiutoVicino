package it.unive.aiutovicino.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.List;
import it.unive.aiutovicino.General;
import it.unive.aiutovicino.adapter.UserAdapter;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentConvalidaCorsiBinding;
import it.unive.aiutovicino.model.UserModel;


public class ConvalidaCorsiFragment extends Fragment {
    private FragmentConvalidaCorsiBinding binding;
    private ListView listCorsi;
    private ProgressBar progressSpinner;
    private UserAdapter userAdapter;
    private List<UserModel> users;
    private View rootView;

    public static ConvalidaCorsiFragment newInstance() {
        ConvalidaCorsiFragment fragment = new ConvalidaCorsiFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConvalidaCorsiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        General.setSearchViewInvisible();
        progressSpinner = binding.progressBarConvalidaCorsi;

        listCorsi =  binding.listConvalidaCorsi;
        userAdapter = new UserAdapter(root.getContext());

        //new Connection().execute();

        listCorsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            return UserController.getNotApprovedUsers(General.user.getId());
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                users = (List<UserModel>)result;
                userAdapter.setUsers(users);
                listCorsi.setAdapter(userAdapter);
            }
            progressSpinner.setVisibility(View.GONE);

        }
    }
}