package com.fst.myapplication.ui.Administration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdministrationViewModel extends ViewModel {



    private MutableLiveData<String> mText;

    public AdministrationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Administration fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
