package it.unive.aiutovicino.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<String> filter;

    public SearchViewModel() {
        filter = new MutableLiveData<>();
    }

    public void setFilter(String s){
        filter.setValue(s);
    }

    public LiveData<String> getFilter() {
        return filter;
    }
}