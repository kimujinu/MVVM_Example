package com.example.mvvm_example.model

import com.example.mvvm_example.model.enum.KakaoSearchSortEnum
import com.example.mvvm_example.model.response.ImageSearchResponse
import com.example.mvvm_example.model.service.KakaoSearchService
import io.reactivex.Single
import retrofit2.http.Query

interface DataModel {
    fun getData(query: String, sort: KakaoSearchSortEnum, page : Int, size:Int) : Single<ImageSearchResponse>
}