package com.fst.myapplication.ui.Filetransfer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FiletransferViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FiletransferViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is File Transfer fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}