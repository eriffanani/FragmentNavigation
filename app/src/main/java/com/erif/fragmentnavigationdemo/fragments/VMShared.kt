package com.erif.fragmentnavigationdemo.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VMShared : ViewModel() {

    private val mutableSelectedItem = MutableLiveData<Boolean>()
    val selectedItem: LiveData<Boolean> get() = mutableSelectedItem

    fun selectItem() {
        mutableSelectedItem.value = true
    }



}