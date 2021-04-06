package com.example.mvvm_example.di

import com.example.mvvm_example.MainSearchRecyclerViewAdapter
import com.example.mvvm_example.model.DataModel
import com.example.mvvm_example.model.DataModelImpl
import com.example.mvvm_example.model.service.KakaoSearchService
import com.example.mvvm_example.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/*

 DI를 하는 실제 코드, 혹은 DI를 위한 설계도
 DI(Dependency Injction) 이란 : Android는 context의 영향을 많이 받는 플랫폼이다.
                                예로 들면 Activity LifeCycle에 따라 자원을 생성하고 사용 할 수 있다,
                                즉, Activity, Fragment내에서 선언되고 사용되는 Instance들은
                                Activity, Fragment의 영향을 받는다는 말이다.
                                Instance 생성 시 내부 환경의 영향을 받는다면, 같은 Instance라도 다른 환경에서
                                다르게 동작 할 수 있다. 하지만 항상 같은 환경에서만 Instance를 생성하고,
                                Activity나 Fragment에서는 생성된 Instance를 받아서 사용만 한다면
                                내부 환경과 상관없이 같은 동작을 하며, 범용적으로 재사용 할 수 있다.
                                이러한 개념을 DI(Dependency Injection)이라고 한다.
 Dependency Injection 사용목적 : 객체 간의 의존관계를 객체-객체가 아닌 외부에서 객체를 생성하고 전달해줌으로써
                                 객체간의 의존성 제거 및 결합도를 낮추는 것.
                         장점 : 모듈간의 의존성이 낮아져 유지보수 및 코드 재사용에 좋다.
                                테스트 용이.
 */
var retrofitPart = module {
    single<KakaoSearchService> {//single : 앱이 실행되는 동안 계속 유지되는 싱글톤 객체 생성.
        Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// Rxjava 설정
            .addConverterFactory(GsonConverterFactory.create()) //GsonConverter 덕분에 서버로부터 응답받은 json을 아무것도 하지않고 바로 data class로 바꿀수 있음.
            .build()
            .create(KakaoSearchService::class.java)
    }
}
/*
    factory : 요청할 때마다 매번 새로운 객체를 생성.
 */
var adapterPart = module {
    factory {
        MainSearchRecyclerViewAdapter()
    }
}
/*
   팩토리외에도 single{}이 존재함. single은 싱글톤패턴처럼 어플리케이션에서 단 하나만 만든다. 보통 Retrofit을 통해 만든 서비스 클래스를 single로 만든다.
   이렇게 얻은 객체들은 get()으로 넣는것 이외에도 by inject()를 통해서도 얻을 수 있다.
 */
var modelPart = module {
    factory<DataModel> {
        DataModelImpl(get()) //DataModelImpl() 클래스를 만들어줌, 다른 클래스에서 해당 부분이 필요하면 단순히 get()을 해주면 팩토리로 만든 클래스가 들어감.
    }
}

var viewModelPart = module {
    viewModel {
        MainViewModel(get())
    }
}

var myDiModule = listOf(retrofitPart, adapterPart, modelPart, viewModelPart)
//다른곳에서 get() 이나 by inject()를 통해 원하는 서비스를 얻어올수 있다.

/*
    DSL이란 : DSL(Domain Specific Language)의 약어, 도메인 특화 언어
    Koin DSL 키워드 : - module : koin 모듈 정의
                     - ViewModel : Activity나 Fragment에 각 ViewModel을 주입
                     - factory : 의존성 주입(inject/get) 시점마다 새로운 객체를 매번 생성
                     - single : 해당 객체를 싱글톤으로 생성 (App LifeCycle 전체동안 단일 인스턴스)
                     - bind : 생성할 객체를 다른 Type으로 바인딩(Class, Interface 상속관계 필요)
                     - get() : Component 내에서 알맞은 의존성 주입
                     - inject() : get과 같이 알맞은 의존성 주입(by inject()방식, val에만 가능, var변수에 사용불가)

     Koin 동작방식 :  1. Module선언(생성)
                     2. Application 단위 Class에서 startKoin()으로 Koin 실행
                     3. 의존성 주입 - 구성요소(Activity, Fragment, Class 등)
 */