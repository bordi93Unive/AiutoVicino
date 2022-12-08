package it.unive.aiutovicino.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.model.UserModel;

public class UserAdapter extends BaseAdapter {
    private LayoutInflater inflter;
    private List<UserModel> users = new ArrayList<>();

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

        return view;
    }
}
