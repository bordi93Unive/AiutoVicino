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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.util.Calendar;
import java.util.Locale;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.R;

import it.unive.aiutovicino.controller.AnnuncioController;
import it.unive.aiutovicino.controller.UserController;
import it.unive.aiutovicino.databinding.FragmentAnnuncioCreaBinding;
import it.unive.aiutovicino.model.AnnuncioModel;
import it.unive.aiutovicino.model.UserModel;
import it.unive.aiutovicino.ui.registrazione.RegistrazioneFragment;


public class AnnuncioCreaFragment extends Fragment {

    private FragmentAnnuncioCreaBinding binding;

    View root;
    EditText description;
    EditText place;
    EditText partecipantsNumber;

    String[] item = { "Pulisci Ugo", "Gioca con Ugo", "Aiuta Ugo","Abbraccia Ugo"};

    Spinner spinner;
    TextView textData,textTime;
    ArrayAdapter<String> adapterItems;
    DatePickerDialog pickerData;
    TimePickerDialog pickerTime;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnnuncioCreaViewModel annuncioCreaViewModel = new ViewModelProvider(this).get(AnnuncioCreaViewModel.class);

        binding = FragmentAnnuncioCreaBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        description = binding.inputDescrizione;
        place = binding.inputLuogo;
        partecipantsNumber = binding.inputPartecipanti;

        /** dropdown menù per le categorie*/
        spinner = binding.inputCategoria;
        //spinner.setOnItemSelectedListener(this);
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_item_annuncio_crea,item);
        spinner.setAdapter(adapterItems);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"Categoria: " + item[i] , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Locale.setDefault(Locale.ITALY);

        /** data picker sul textEdit Data*/
        textData = binding.inputData;
        //textData.setInputType(InputType.TYPE_NULL); messo su xml della box
        textData.setOnClickListener(new View.OnClickListener() {
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
                                textData.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                pickerData.show();
            }
        });

        /** time picker sul textEdit Orario*/
        textTime = binding.inputOrario;
        textTime.setInputType(InputType.TYPE_NULL);
        textTime.setOnClickListener(new View.OnClickListener() {
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
                                textData.setText(hour+ ":" + minutes);
                            }

                        }, hour,minutes,true);
                pickerTime.show();
            }
        });

        binding.buttonCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        protected Object doInBackground(Object... arg0){
            AnnuncioModel annuncio = new AnnuncioModel();
            annuncio.description = description.getText().toString();
            annuncio.id_category = "1";
            annuncio.place = place.getText().toString();
            annuncio.partecipantsNumber = Integer.valueOf(partecipantsNumber.getText().toString());

            return AnnuncioController.insertAnnuncio(annuncio);
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result == null || (Boolean)result == false){
                createError();
            } else {
                createOk();
            }
        }
    }

}