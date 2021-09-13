package com.router.certificationplatform.ui.board

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchBoardInfo(board_info : String) {

        myRef.child(board_info).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }


}