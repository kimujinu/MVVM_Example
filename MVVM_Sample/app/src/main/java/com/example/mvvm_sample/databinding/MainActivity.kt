package com.example.mvvm_sample.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_sample.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        binding.activity = this@MainActivity
        setRev()
        setObserv()
    }

    fun btnClick(view : View) {
        Toast.makeText(this,"Button Click",Toast.LENGTH_LONG).show()
    }

    fun setRev() {
        val profileAdapter =
            ProfileAdapter(this)
        binding.mainRcv.layoutManager = LinearLayoutManager(this)
        binding.mainRcv.adapter = profileAdapter
        profileAdapter.data = listOf(
            ProfileData(
                profile = "https://mblogthumb-phinf.pstatic.net/MjAxOTAyMDZfMTQ5/MDAxNTQ5NDU0ODg2MzA2.Tibh6Bswpgxp6Jba6epg1iP95UeJhmq2ibMy_HO2EEAg.pxckRXoLrHp9AekkBRTx_iQ56mfHEHI1SIlsGMsKgAAg.JPEG.tjdwkd2012/SE-4d25aa20-1ce9-4569-aee5-db9cee07c088.jpg?type=w800",
                name = "Kim",
                age = 25
            ),
            ProfileData(
                profile = "https://mblogthumb-phinf.pstatic.net/MjAxOTAyMDZfMTQ5/MDAxNTQ5NDU0ODg2MzA2.Tibh6Bswpgxp6Jba6epg1iP95UeJhmq2ibMy_HO2EEAg.pxckRXoLrHp9AekkBRTx_iQ56mfHEHI1SIlsGMsKgAAg.JPEG.tjdwkd2012/SE-4d25aa20-1ce9-4569-aee5-db9cee07c088.jpg?type=w800",
                name = "King",
                age = 24
            )
        )
        profileAdapter.notifyDataSetChanged()
    }
    fun setObserv(){
        var item : ObservableData = ObservableData()
        item.site = "Naver"
        binding.site = item

        Handler().postDelayed(Runnable {
            run {
                item.site = "Google"
            }
        },3000)
    }


}
