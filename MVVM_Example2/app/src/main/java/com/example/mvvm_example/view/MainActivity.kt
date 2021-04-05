package com.example.mvvm_example.view

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvm_example.viewmodel.MainViewModel
import com.example.mvvm_example.MainSearchRecyclerViewAdapter
import com.example.mvvm_example.R
import com.example.mvvm_example.base.BaseKotlinActivity
import com.example.mvvm_example.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Observer

/*
    MVVM 패턴을 위한 Livedata,
    통신을 위한 Retrofit + RxJava,
    이미지를 뿌려주기 위한 Picasso,
    의존성주입을 위한 Koin,
    com.android.support를 대체하는 AndroidX
 */

class MainActivity : BaseKotlinActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    /*
        만약 MVVM 패턴에서 ViewModel을 테스팅한다고 가정하면,
        ViewModel을 테스팅하려면 ViewModel이 의존하고 있는 모델은 어떻게 만들어서 ViewModel에게 줘야 ViewModel을 테스팅 할 수 있을까?
            -> 간단하게 Model을 내부에서 생성하지 않고 생성자의 인자로 주는 것.
                -> 틀린 예시) ViewModel{
                                val model = Model()
                              }
                -> 맞은 예시) ViewModel(val model){}
                        => 이렇게 하면 ViewModel을 테스팅용 모델을 ViewModel의 생성자로 injection 해줌으로써 ViewModel을 테스팅하기 쉬워짐.
         그 후 테스팅 하기 위해 DI(Dependency Injection)을 해야하는데 이 작업을 위한 라이브러리가 존재(Dagger2, Koin ...)
     */
    override val viewModel: MainViewModel by viewModel()//Koin을 통한 의존성(Dependcy Injection) 주입(테스팅을 위함)
    private val mainSearchRecyclerViewAdapter: MainSearchRecyclerViewAdapter by inject()


    override fun initStartView() {

        main_activity_search_recycler_view.run {

            adapter = mainSearchRecyclerViewAdapter
            layoutManager = StaggeredGridLayoutManager(3, 1).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
                orientation = StaggeredGridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }


    }

    override fun initDataBinding() {
        viewModel.imageSearchResponseLiveData.observe(this, androidx.lifecycle.Observer {
            it.documents.forEach { document ->
                mainSearchRecyclerViewAdapter.addImageItem(document.image_url, document.doc_url)
            }
            mainSearchRecyclerViewAdapter.notifyDataSetChanged()
        })
    }


    override fun initAfterBinding() {
        main_activity_search_button.setOnClickListener {
            viewModel.getImageSearch(main_activity_search_text_view.text.toString(), 1, 80)
        }
    }
}
/*
    MVVM 패턴이란 ? MVP 패턴에서 Presenter와 View, 그리고 Model간의 양방향 의존성이 너무 깊어서
                    그 단점을 개선한 패턴.
                    View는 ViewModel의 참조를 가지고 있지만, ViewModel은 View의 참조를 가지고 있지 않고
                    ViewModel도 Model의 참조를 가지고 있지만, Model은 ViewModel의 참조를 가지고 있지 않다

     * 그렇다면 ViewModel은 StartActivity나 Snackbar등, 어떻게 View의 함수를 호출할 수 있을까?
        --> View가 ViewModel을 Binding 하고 있으면 됨.
            그렇게되면 ViewModel은 단순히 값을 바꾸기만하고, View는 그 값이 바뀌는걸 관찰하면 됨.
 */