package com.router.certificationplatform.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.router.certificateplatform.repository.QnetService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainAcitivtyViewModel : ViewModel() {

    private val qnetService: QnetService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(QnetService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        qnetService = retrofit.create(QnetService::class.java)
        Log.d(TAG, "fetchInformation: " + retrofit.baseUrl())
    }

    //종목명 목록 가져오기
    val certificateList = ArrayList<String>()
    val certificateListLiveData = MutableLiveData<ArrayList<String>>()
    fun fetchCertificateList(page: Int, perPage: Int, APIKey: String) {
        viewModelScope.launch {

            val infomation = qnetService.getInformation(page, perPage, APIKey)
            for (data in infomation.data) {
                certificateList.add(data.종목명)
            }
            certificateListLiveData.value = certificateList
        }
    }
}