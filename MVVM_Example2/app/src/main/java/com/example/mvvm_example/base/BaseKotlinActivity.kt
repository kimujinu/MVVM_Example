package com.example.mvvm_example.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/*
    BaseKotlinActivity<ActivitySbsMainBinding>
    와 같이 상속받을 때, ActivitySbsMainBinding 과 같은 파일이 자동생성되지 않는다면
    1. 해당 액티비티의 레이아웃이 <layout></layout>으로 감싸져 있는지 확인
    2. 다시 빌드 수행 or 클린 빌드 후 다시 빌드 수행
    3. 이름 확인 : sbs_main_activity => ActivitySbsMainBinding
 */

abstract class BaseKotlinActivity<T: ViewDataBinding, R: BaseViewModel> : AppCompatActivity() { //모든 액티비티는 이 BaseActivity를 구현하게 됨.

    /*
     viewDataBinding은 액티비티의 layout을 빌드하면 자동 생성되는 클래스.
     하지만 그냥 빌드하면 생성되는 것은 아니고 layout에 한가지 작업이 필요.
     ex) .xml 파일에 <layout></layout>으로 감싸야함.
     */

    lateinit var viewDataBinding: T
    /*
     setContentView로 호출할 Layout의 리소스 Id.
     ex) R.layout.activity_sbs_main

     */
    
    abstract val layoutResourceId : Int

    /*
     viewModel로 쓰일 변수.
     액티비티가 BaseKotlinActivity를 구현할 때, ViewDataBinding 클래스 뿐만 아니라 ViewModel 클래스도 제네릭으로 주게됨.
     BaseView에서도 ViewModel을 참조 할 수 있게 한 이유는 SnackBar Observing을 위해서
     */
    abstract val viewModel : R

    /*
     레이아웃을 띄운 직후 호출
     뷰나 액티비티의 속성 등을 초기화
     ex) 리사이클러뷰, 툴바, 드로어뷰
     */
    abstract fun initStartView()

    /*
     두번째로 호출
     데이터 바인딩 및 RxJava설정.
     ex) RxJava observe, databinding observe.
     */
    abstract fun initDataBinding()

    /*
     바인딩 이후에 할 일을 여기에 구현
     그 외에 설정할 것이 있으면 이곳에서 설정.
     클릭 리스너도 이곳에서 설정.
     */
    abstract fun initAfterBinding()
    
    private var isSetBackButtonValid = false
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this,layoutResourceId)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }
    
}