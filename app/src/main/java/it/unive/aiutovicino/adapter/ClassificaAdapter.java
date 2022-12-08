package it.unive.aiutovicino.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.model.RankingModel;

public class ClassificaAdapter extends BaseAdapter {
    private LayoutInflater inflter;
    private RankingModel[] rankings;
    private Context context;

    public ClassificaAdapter(Context context, RankingModel[] rankings){
        this.inflter = (LayoutInflater.from(context));
        this.rankings = rankings;
    }

    @Override
    public int getCount() {
        return this.rankings.length;
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
        view = inflter.inflate(R.layout.adapter_classifica, null);
        TextView posizione = (TextView)view.findViewById(R.id.adapter_classifica_rank);
        TextView nickname = (TextView)view.findViewById(R.id.adapter_classifica_nickname);
        TextView coins = (TextView)view.findViewById(R.id.adapter_classifica_score);
        ImageView icon = (ImageView) view.findViewById(R.id.adapter_classifica_icon);

        nickname.setText(this.rankings[i].nickname);
        posizione.setText("Rank " + this.rankings[i].position);
        coins.setText("Coin " + this.rankings[i].coins);


        String mipmapName = "ic_" +  this.rankings[i].nickname.toLowerCase().substring(0,1);
        int resID = view.getResources().getIdentifier(mipmapName , "mipmap", view.getContext().getPackageName());
        icon.setImageResource(resID);


        return view;
    }
}
