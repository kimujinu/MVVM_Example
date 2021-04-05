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
 */
var retrofitPart = module {
    single<KakaoSearchService> {
        Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// Rxjava 설정
            .addConverterFactory(GsonConverterFactory.create()) //GsonConverter 덕분에 서버로부터 응답받은 json을 아무것도 하지않고 바로 data class로 바꿀수 있음.
            .build()
            .create(KakaoSearchService::class.java)
    }
}

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