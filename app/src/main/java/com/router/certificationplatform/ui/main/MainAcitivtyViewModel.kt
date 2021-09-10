package com.router.certificationplatform.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.router.certificateplatform.repository.QnetService
import com.router.certificationplatform.GlobalApplication
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import org.json.JSONArray
import java.lang.reflect.Type


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

    //즐겨찾기 목록 가져오기
    val database = Firebase.database
    val myRef = database.getReference("즐겨찾기")
    val starList = ArrayList<String>()
    val starListLiveData = MutableLiveData<ArrayList<String>>()


    val starLoadingLiveData = MutableLiveData<Boolean>()
    fun fetchStarList(){
        starLoadingLiveData.value = true
        starList.clear()
        myRef.child(GlobalApplication.user.uid).get().addOnSuccessListener {
            //받아온 json 을 starlist arrayList에 삽입
            it.children.forEach {
                starList.add(it.key.toString())
            }
            starListLiveData.value = starList
            starLoadingLiveData.value = false
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}