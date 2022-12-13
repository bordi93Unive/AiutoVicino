package it.unive.aiutovicino.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.model.UserModel;

public class UserAdapter extends BaseAdapter {
    private LayoutInflater inflter;
    private List<UserModel> users = new ArrayList<>();
    private int index = -1;
    private ProgressBar progressBar;

    public UserAdapter(Context context){
        this.inflter = (LayoutInflater.from(context));
    }


    public void setUsers(List<UserModel> users){
        this.users = users;
    }

    @Override
    public int getCount() {
        return this.users.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.adapter_users, null);
        TextView name = (TextView)view.findViewById(R.id.adapter_users_name_surname);
        TextView email = (TextView)view.findViewById(R.id.adapter_users_email);
        name.setText(users.get(i).getName() + " " + users.get(i).getSurname());
        email.setText(users.get(i).getEmail());

        progressBar = view.findViewById(R.id.progress_bar_convalida);
        ImageButton confirm = view.findViewById(R.id.adapterUserButtonConfirm);
        ImageButton cancel = view.findViewById(R.id.adapterUserButtonCancel);

        confirm.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = i;
                new ConnectionConfirm().execute();
            }
        });

        cancel.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = i;
                new ConnectionDelete().execute();
            }
        });
        return view;
    }

    private void removeItem(){
        users.remove(index);
        notifyDataSetChanged();
    }

    private class ConnectionConfirm extends AsyncTask {
        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Object doInBackground(Object... arg0)
        {
            UserModel user = users.get(index);
            return UserController.approveUser(user.getId());
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                if((Boolean)result == true){
                    removeItem();
                }
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    private class ConnectionDelete extends AsyncTask {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Object doInBackground(Object... arg0)
        {
            UserModel user = users.get(index);
            return UserController.deleteUser(user.getId());
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result!= null) {
                if((Boolean)result == true){
                    removeItem();
                }

            }
            progressBar.setVisibility(View.GONE);
        }
    }
}
