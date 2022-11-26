package it.unive.aiutovicino.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.model.AnnuncioModel;
import it.unive.aiutovicino.model.RankingModel;

public class ClassificaAdapter extends BaseAdapter {
    private LayoutInflater inflter;
    private RankingModel[] rankings;

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
        TextView posizione = (TextView)view.findViewById(R.id.adapter_classifica_posizione);
        TextView name = (TextView)view.findViewById(R.id.adapter_classifica_name);
        TextView coins = (TextView)view.findViewById(R.id.adapter_classifica_coins);
        ImageView icon = (ImageView) view.findViewById(R.id.adapter_classifica_icon);

        posizione.setText(this.rankings[i].position + ".");
        name.setText(this.rankings[i].name + this.rankings[i].surname);
        coins.setText("" + this.rankings[i].coins);

        String mipmapName = "ic_" + this.rankings[i].name.toLowerCase().substring(0,1);
        /*int resID = getResources().getIdentifier(mipmapName , "mipmap", getPackageName());
        icon.setImageResource(resID);*/


        return view;
    }
}
