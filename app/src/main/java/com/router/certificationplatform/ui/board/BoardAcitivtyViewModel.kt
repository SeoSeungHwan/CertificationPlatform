package com.router.certificationplatform.ui.board

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.router.certificationplatform.GlobalApplication
import com.router.certificationplatform.model.Board
import java.util.*
import kotlin.collections.ArrayList


class BoardAcitivtyViewModel : ViewModel() {

    val starLiveData = MutableLiveData<Boolean>()
    val database = Firebase.database
    val starRef = database.getReference("즐겨찾기")
    val boardRef = database.getReference("게시판")


    //즐겨찾기가 되어있는지 확인 후 별 체크
    fun checkStar(certificate_name : String){
        starRef.child(GlobalApplication.user.uid).child(certificate_name).get().addOnSuccessListener {
           starLiveData.value = it.value != null
       }.addOnFailureListener {
           Log.d(TAG, "checkStar: fail")
       }
    }
    fun addStar(certificate_name : String){
        starRef.child(GlobalApplication.user.uid).child(certificate_name).setValue("true")
    }
    fun removeStar(certificate_name : String){
        starRef.child(GlobalApplication.user.uid).child(certificate_name).removeValue()
    }

    //게시글 목록 가져오기
    val boardList = ArrayList<Board>()
    val boardListLivedata = MutableLiveData<ArrayList<Board>>()
    fun fetchBoard(certificate_name: String){
        boardList.clear()
        boardRef.child(certificate_name).get().addOnSuccessListener {
            it.children.forEach {
                val Board = it.getValue(Board::class.java)
                if (Board != null) {
                    boardList.add(Board)
                }
            }
            boardList.reverse()
            boardListLivedata.value = boardList
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}
