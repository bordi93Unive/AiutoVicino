package it.unive.aiutovicino.ui.annuncio_crea;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;
import it.unive.aiutovicino.controller.AnnouncementController;
import it.unive.aiutovicino.databinding.FragmentAnnuncioCreaBinding;
import it.unive.aiutovicino.model.AnnouncementModel;
import it.unive.aiutovicino.model.CategoryModel;


public class AnnuncioCreaFragment extends Fragment {

    private FragmentAnnuncioCreaBinding binding;

    View root;
    EditText description;
    EditText place;
    EditText partecipantsNumber;
    EditText coin;
    EditText date;
    EditText time;
    EditText title;
    ProgressBar progressSpinner;
    Spinner spinner;
    ArrayAdapter<String> adapterItems;
    DatePickerDialog pickerData;
    TimePickerDialog pickerTime;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnnuncioCreaViewModel annuncioCreaViewModel = new ViewModelProvider(this).get(AnnuncioCreaViewModel.class);

        binding = FragmentAnnuncioCreaBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        progressSpinner = binding.progressBarCreaAnnuncio;

        title = binding.inputTitle;
        description = binding.inputDesription;
        place = binding.inputPlace;
        partecipantsNumber = binding.inputPartecipanti;
        date = binding.inputDate;
        time = binding.inputTime;
        coin = binding.inputCoin;

        /** dropdown menù per le categorie*/
        //new Categoria().execute();

        String[] items = new String[General.categories.size()];
        int index = 0;
        for(CategoryModel categoria : General.categories){
            items[index++] = categoria.getDescription();
        }

        spinner = binding.inputCategoria;
        //spinner.setOnItemSelectedListener(this);
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_item_annuncio_crea, items);
        spinner.setAdapter(adapterItems);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryModel categoria = General.categories.get(i);
                coin.setText(String.valueOf(categoria.getCoins()) );
                coin.setEnabled(categoria.getDescription().equals("Corso"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /** data picker sul textEdit Data*/
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                pickerData = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pickerData.show();
            }
        });

        /** time picker sul textEdit Orario*/
        //non perdete tempo per i fusi orari, si localizza in base al timezone del telefono in uso
        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                //se a TimePickerDialog non passo il secondo parametro allora viene in stile orologio
                pickerTime = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener () {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                                time.setText(hour+ ":" + minutes);
                            }

                        }, hour,minutes,true);
                pickerTime.show();
            }
        });

        binding.buttonCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date.getText().toString().isEmpty()) {
                    date.setError("Compilare il campo data!");
                    date.requestFocus();
                    return;
                }
                if(time.getText().toString().trim().isEmpty()) {
                    time.setError("Compilare il campo orario!");
                    time.requestFocus();
                    return;
                }
                if(partecipantsNumber.getText().toString().trim().isEmpty()) {
                    partecipantsNumber.setError("Compilare il campo partecipanti!");
                    partecipantsNumber.requestFocus();
                    return;
                }
                if(place.getText().toString().trim().isEmpty()) {
                    place.setError("Compilare il campo luogo!");
                    place.requestFocus();
                    return;
                }
                if(description.getText().toString().trim().isEmpty()) {
                    description.setError("Compilare il campo descrizione!");
                    description.requestFocus();
                    return;
                }
                if(coin.getText().toString().trim().isEmpty()) {
                    coin.setError("Compilare il campo coin!");
                    coin.requestFocus();
                    return;
                }

                new Connection().execute();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void createError(){
        Snackbar.make(root, "Errore durante la creazione", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void createOk(){
        Snackbar.make(root, "Annuncio creato", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private class Connection extends AsyncTask {

        @Override
        protected void onPreExecute() {

            progressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... arg0){
            CategoryModel categoria = General.categories.get(spinner.getSelectedItemPosition());

            String mydate = "";
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date_to_format = (Date)formatter.parse(date.getText().toString());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                mydate = dateFormat.format(date_to_format);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            AnnouncementModel annuncio = new AnnouncementModel();
            annuncio.setId(General.user.getId());
            annuncio.setIdCategory(categoria.getId());
            //annuncio.setTitle(title.getText().toString());
            annuncio.setDescription(description.getText().toString());
            annuncio.setPlace(place.getText().toString());
            annuncio.setDate(mydate);
            annuncio.setHours(time.getText().toString());
            annuncio.setParticipantsNumber(Integer.parseInt(partecipantsNumber.getText().toString()));
            annuncio.setCoins(Integer.parseInt(coin.getText().toString()));

            return AnnouncementController.insert(annuncio);
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result == null || !((Boolean) result)){
                createError();
            } else {
                createOk();
                //reindirizzo al fragment Miei Annunci
                Navigation.findNavController(getView()).navigate(R.id.action_navAnnuncioCrea_to_navAnnunci);
            }
            progressSpinner.setVisibility(View.GONE);
        }
    }
}