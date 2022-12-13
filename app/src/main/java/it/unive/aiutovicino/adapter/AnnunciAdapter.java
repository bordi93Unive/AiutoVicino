package it.unive.aiutovicino.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.unive.aiutovicino.R;
import it.unive.aiutovicino.model.AnnouncementModel;

public class AnnunciAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater inflter;
    private List<AnnouncementModel> annunci = new ArrayList<>();
    private List<AnnouncementModel> announcementsOriginal = null;

    public AnnunciAdapter(Context context){
        this.inflter = (LayoutInflater.from(context));
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
        int participantsApplied = 0;

        view = inflter.inflate(R.layout.adapter_annunci, null);
        TextView title = (TextView)view.findViewById(R.id.adapter_corsi_title);
        TextView date_time = (TextView)view.findViewById(R.id.adapter_corsi_date_time);
        TextView participants = (TextView)view.findViewById(R.id.adapter_corsi_partecipanti);
        ImageView icon = (ImageView) view.findViewById(R.id.adapter_corsi_icon);
        AnnouncementModel announcement = this.annunci.get(i);
        title.setText(announcement.getTitle());
        date_time.setText("Il giorno " + announcement.getDate() + " alle ore " + announcement.getHours());

        if(announcement.getUserApplyed() != null )
            participantsApplied = announcement.getUserApplyed().size();

        participants.setText("Applicazioni: " + participantsApplied + "/" + announcement.getParticipantsNumber());


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

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                annunci = (List<AnnouncementModel>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<AnnouncementModel> FilteredArrList = new ArrayList<AnnouncementModel>();

                if (announcementsOriginal == null) {
                    announcementsOriginal = new ArrayList<AnnouncementModel>(annunci); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {

                    results.count = announcementsOriginal.size();
                    results.values = announcementsOriginal;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < announcementsOriginal.size(); i++) {
                        String data = announcementsOriginal.get(i).getTitle();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(announcementsOriginal.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

}
