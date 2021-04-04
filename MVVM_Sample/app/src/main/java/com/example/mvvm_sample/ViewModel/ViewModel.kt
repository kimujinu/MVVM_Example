package com.example.mvvm_sample.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ViewModel (application: Application) : AndroidViewModel(application){
    //ViewModel()을 상속받을 경우
    // class MainViewModel():ViewModel(){}

    //LiveData
    //값이 변경되는 경우 MutableLiveData로 선언한다. LiveData 객체의 값이 변경될 경우에는 MutableLiveData<T>()으로 선언해줌.
    var count = MutableLiveData<Int>()

    init {
        count.value = 0
    }

    fun increase() {
        count.value = count.value?.plus(1)
    }

    fun decrease() {
        count.value = count.value?.minus(1)
    }
}