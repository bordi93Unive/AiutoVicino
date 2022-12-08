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
import it.unive.aiutovicino.model.AnnouncementModel;
import it.unive.aiutovicino.model.UserModel;

public class AnnunciAdapter extends BaseAdapter {
    private LayoutInflater inflter;
    private List<AnnouncementModel> annunci = new ArrayList<>();
    List<UserModel> usersApplyed;
    int partecipanti;

    public AnnunciAdapter(Context context){
        this.inflter = (LayoutInflater.from(context));
    }
    public AnnunciAdapter(Context context, List<AnnouncementModel> annunci){
        this.inflter = (LayoutInflater.from(context));
        this.annunci = annunci;
    }

    public void setAnnunci(List<AnnouncementModel> annunci){
        this.annunci = annunci;
    }

    @Override
    public int getCount() {
        return this.annunci.size();
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
        TextView date_time = (TextView)view.findViewById(R.id.adapter_annunci_date_time);
        TextView participants = (TextView)view.findViewById(R.id.adapter_annunci_partecipanti);
        ImageView icon = (ImageView) view.findViewById(R.id.adapter_annunci_icon);
        AnnouncementModel announcement = this.annunci.get(i);
        title.setText(announcement.getTitle());
        date_time.setText("Il giorno " + announcement.getDate() + " alle ore " + announcement.getHours());

        usersApplyed = announcement.getUserApplyed();
        if(announcement.getUserApplyed() == null )
            partecipanti = 0;
        else
            partecipanti = usersApplyed.size();

        participants.setText("Applicazioni: " + partecipanti + "/" + announcement.getParticipantsNumber());


        switch(this.annunci.get(i).getIdCategory()){
            case "1":
                icon.setImageResource(R.drawable.category_corsi);
                break;
            case "2":
                icon.setImageResource(R.drawable.category_bambini);
                break;
            case "3":
                icon.setImageResource(R.drawable.category_anziani);
                break;
            case "4":
                icon.setImageResource(R.drawable.category_cani);
                break;
            case "5":
                icon.setImageResource(R.drawable.category_trasporti);
                break;
        }

        return view;
    }
}
