package com.example.aiutovicino.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aiutovicino.R;
import com.example.aiutovicino.model.AnnuncioModel;

public class AnnunciAdapter extends BaseAdapter {
    private LayoutInflater inflter;
    private AnnuncioModel[] annunci;

    public AnnunciAdapter(Context context, AnnuncioModel[] annunci){
        this.inflter = (LayoutInflater.from(context));
        this.annunci = annunci;
    }

    @Override
    public int getCount() {
        return this.annunci.length;
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
        view = inflter.inflate(R.layout.adapter_annunci, null);
        TextView title = (TextView)view.findViewById(R.id.adapter_annunci_title);
        TextView description = (TextView)view.findViewById(R.id.adapter_annunci_description);
        ImageView icon = (ImageView) view.findViewById(R.id.adapter_annunci_icon);
        title.setText(this.annunci[i].title);
        description.setText(this.annunci[i].description);
        switch(this.annunci[i].id_category){
            case 1:
                icon.setImageResource(R.drawable.category_anziani);
                break;
            case 2:
                icon.setImageResource(R.drawable.category_bambini);
                break;
            case 3:
                icon.setImageResource(R.drawable.category_cani);
                break;
        }

        return view;
    }
}
