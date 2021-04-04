package com.example.mvvm_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.mvvm_sample.ViewModel.ViewModel
import com.example.mvvm_sample.databinding.ActivityMainBinding

class MVVM_sample : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding
    private val model : ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_m_v_v_m_sample)

        mBinding.lifecycleOwner = this
        mBinding.viewModel = model
    }
}
