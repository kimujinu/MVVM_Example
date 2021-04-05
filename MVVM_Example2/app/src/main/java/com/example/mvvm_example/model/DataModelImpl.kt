package com.example.mvvm_example.model

import com.example.mvvm_example.model.enum.KakaoSearchSortEnum
import com.example.mvvm_example.model.response.ImageSearchResponse
import com.example.mvvm_example.model.service.KakaoSearchService
import com.example.mvvm_example.viewmodel.MainViewModel
import io.reactivex.Single

/*
 실제로 DataModelImpl에서 생성자의 인자로 서비스를 얻어온다.
 */
class DataModelImpl(private val service : KakaoSearchService): DataModel {
    private val KAKAO_APP_KEY = "ef26df2d529749b3aeda009832b94c47"

    //Single을 반환해주는 역할.
    override fun getData(query:String, sort: KakaoSearchSortEnum, page:Int, size:Int) : Single<ImageSearchResponse> {
        return service.searchImage(auth = "KakaoAK $KAKAO_APP_KEY",query = query, sort = sort.sort, page = page, size = size)

    }
}