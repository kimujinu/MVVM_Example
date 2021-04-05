package com.example.mvvm_example.util

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/*
    SingleLiveEvent의 역할 : 만약 startActivityLiveData를 Observe하고 있는 액티비티가 회전을 하게된다면,
                             Activity는 startActivityLiveData가 이전에 가지고 있던 value인 SOME_DATA를 다시 observe하게 되고,
                             그럼 startActivity를 통해 새로운 액티비티가 실행된다.
                             이처럼 LiveData는 1번의 이번트를 Observing하려고 하기엔 문제점이 있다. 이것을 보완하기 위해 SingleLiveEvent를 사용한다.

 */
open class SingleLiveEvent<T> : MutableLiveData<T>()  {
    private val mPending = AtomicBoolean(false)

    /*
        compareAndSet과 setValue의 true로 바꿔주는 작업을 통하여, setValue를 한 번하면  observer의 코드도 한번만 실행됨.
        이렇게 setValue를 한 번 했다면 observer는 두 번할 수없게 함으로써, 한번의 이벤트에 대한 Observing을 구현한다.
     */
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {

        private val TAG = "SingleLiveEvent"
    }
}