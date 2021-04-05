package com.example.mvvm_example.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/*
    ViewModel이 상속받을 BaseViewModel

    다른 ViewModel은 이제 이 BaseViewModel을 상속받게 됨.
 */
open class BaseViewModel : ViewModel() {
    /*
        RxJava의 Observing을 위한 부분
        addDisposable을 이용하여 추가하기만 하면됨.

        Model에서 들어오는 Single<>과 같은 RxJava 객체들의 Observing을 위한 부분
        기본적으로 RxJava의 Observable들은 compositeDisposable에 추가를 해주고,
        ViewModel이 없어질 때 추가했던 것들을 지워줘야 한다.
     */

    private val compositeDisposable = CompositeDisposable()

    /*
     Observable들을 옵저빙할때 addDisposable()을 쓰게됨.
     compositeDisposable라는 곳에 추가하고 ViewModel이 사라질때 (onCleared) 추가한 것을 다 지운다.
     그 이유는 Observing을 계속하면 메모리 누수가 생기기 때문에 데이터 구독을 시작하며
     compositeDisposable에 추가하고 Observing을 그만뒈 될때 compositeDisposable를 비워줌으로써
     메모리 누수를 방지하는 작업.
    */
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    /*
       ViewModel은 View와 lifecycle을 공유하기 때문에 View가 onDestory()될 때,
       ViewModel의 onCleared()가 호출되게 되며, 그에 따라 Observable들이 전부 클리어되게 됨.
     */
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

/*
    Activity가 참조할 뷰모델을 만들고 그러한 뷰모델을 뷰가 참조하고, 데이터 바인딩을 수행하도록 만들어야함.
    기본적으로 ViewModel은 android.lifecycle.ViewModel을 상속받으면 끝이긴하나
    그 외에 할 일이 많기 때문에 android.lifecycle.ViewModel을 상속받는 BaseViewModel을 만듬.
 */

/*
    사용예시

    addDisposable(model.requestToServer(senderInfo)
        .subscribe(Schedulers.io()
        .observeOn(AndroidSchedulers.mainThread()
        .subscribe({
            //성공적인 응답
        },{
            //에러
       }))
 */