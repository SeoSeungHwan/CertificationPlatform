package com.router.certificationplatform.ui.board

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.router.certificationplatform.GlobalApplication
import com.router.certificationplatform.model.Board
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class BoardInfoViewModel : ViewModel() {


    val database = Firebase.database
    val myRef = database.getReference("게시판")

    val boardLiveData = MutableLiveData<Board>()
    fun fetchBoardInfo(certificate_name: String, board_id : String) {

        myRef.child(certificate_name).child(board_id).get().addOnSuccessListener {
            boardLiveData.value = it.getValue(Board::class.java)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }


}