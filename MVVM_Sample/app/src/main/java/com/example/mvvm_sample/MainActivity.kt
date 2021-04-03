package com.example.mvvm_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
/*
    MVVM 동작 순서 : 1. 사용자의 Action이 View를 통해 들어온다.
                    2. Command패턴을 이용해 ViewModel에 Action을 전달한다.
                    3. ViewModel이 Model에서 데이터를 요청하고, Model은 ViewModel에서 요청받은 데이터를 전달
                    4. ViewModel은 응답받은 데이터를 가공, 저장
                    5. View는 ViewModel과의 Data Binding을 이용해 화면을 갱신한다.

             장점 : View와 Model 사이의 의존성이 없음.
                   View와 ViewModel사이의 의존성 또한 존재하지 않는다.
                   각 부분들은 독립적이기 때문에 개별적 개발이 가능.
                                    
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
