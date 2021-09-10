package com.router.certificationplatform.ui.board

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.router.certificationplatform.GlobalApplication


class BoardAcitivtyViewModel : ViewModel() {

    val starLiveData = MutableLiveData<Boolean>()
    val database = Firebase.database
    val myRef = database.getReference("즐겨찾기")


    //즐겨찾기가 되어있는지 확인 후 별 체크
    fun checkStar(certificate_name : String){
       myRef.child(GlobalApplication.user.uid).child(certificate_name).get().addOnSuccessListener {
           starLiveData.value = it.value != null
       }.addOnFailureListener {
           Log.d(TAG, "checkStar: fail")
       }
    }
    fun addStar(certificate_name : String){
        myRef.child(GlobalApplication.user.uid).child(certificate_name).setValue("true")
    }
    fun removeStar(certificate_name : String){
        myRef.child(GlobalApplication.user.uid).child(certificate_name).removeValue()
    }

}
