package com.example.mvvm_sample

import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_sample.databinding.RcvListItemBinding

class ProfileVH(val binding : RcvListItemBinding) :  RecyclerView.ViewHolder(binding.root){
    fun onBind(data : ProfileData){
        binding.user = data
    }
}

/*
데이터 바인딩을 사용하지 않았을 경우 코드
class BaseVH(view : View) : RecyclerView.ViewHolder(view){
        val name : TextView = view.findViewById(R.id.item_name)
        val age : TextView = view.findViewById(R.id.item_age)

        fun onbind(data : ProfileData){
            name.text = data.name
            age.text = data.age.toString()
        }

}
 */