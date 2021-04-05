package com.example.mvvm_example.model.service

import com.example.mvvm_example.model.response.ImageSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface KakaoSearchService {
    @GET("/v2/search/image")
    fun searchImage(
        @Header("Authorization") auth:String,
        @Query("query") query:String,
        @Query("sort") sort:String,
        @Query("page") page:Int,
        @Query("size") size:Int
    ): Single<ImageSearchResponse> //Single : 데이터를 한번 뿌린다.
}                                  //그 외 : Observable, Subject 등등이 존재.

/*
    RxJava는 발행과 구독으로 표현이 가능.
    누군가는 데이터의 흐름속에 데이터를 흘려보내고(발행) 위의 소스코드가 발행 작업.
    누군가는 데이터의 흐름속에서 데이터를 줍는다.(구독)
    Single 객체를 통해 ImageSearchResponse라는 데이터를 발행.
    데이터를 처리하고 싶은 곳에서는 데이터를 구독을 해야한다.
    데이터 구독을 위한 코드가 subscribeOn, observeOn, Subscribe 이다.
    subscribeOn : 데이터를 데이터 흐름속으로 흘려보내는 쓰레드(스케줄러)를 지정하는 작업.
                  정확하게는 데이터를 발행(연산)하는 스케줄러를 지정하는 작업.
                  종류로는 Schedulers.io(), 메인스케줄러, 뉴 스케줄러 등등..

    observeOn : 데이터를 줍는 쓰레드(스케줄러)를 지정하는 작업.
                데이터를 구독하는 스케줄러를 지정하는 작업.
                종류 AndroidSchedulers.mainThread() 메인쓰레드를 쓰는 이유는 안드로이드 UI 작업을 위함.

    subscribe : 네트워킹을 통한 데이터를 줍는 작업.
                ex)service.listRepos("USER")
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe({
                    name_text_view = it.name // Networking Suc
                  }, {
                    Log.d(TAG, "ERROR message : ${it.message}") //Networking fail
                  }))

 */

/*
기존 레트로핏 인터페이스
ex)
    interface GitHubService {
        @GET("/users/{user}/repos")
        fun listRepos(@Path("user") user:String): Call<Repo>
    }
    이렇게 해주고 해당 서비스를 create

     val service:GitHubService = Retrofit.Builder()
                                .baseUrl("https://api.github.com")
                                .build()
                                .create(GitHubService::class.java);
    create 하고 난후 네트워킹 하려고 하는 위치에
    val repos: Call<Repo> = service.listRepos("USER")
    적고 Call객체를 얻어오면 된다.
    그 후 얻어온 Call 객체를

    repos.enqueue(new Callback<Repo>() {
    @Override
    public void onResponse(Response<List<Contributor>> response, Retrofit retrofit) {
        // handle success
    }

    @Override
    public void onFailure(Throwable t) {
        // handle failure
    }
    });
    작성하면 비동기적 네트워킹이 가능하다. 하지만 RxJava에서는 Call를 쓰지않는다.

 */