package com.example.mvvm_sample.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR


class ObservableData : BaseObservable(){
    @get:Bindable
    var site : String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.site)
        }
}

