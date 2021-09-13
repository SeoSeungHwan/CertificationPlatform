package com.router.certificationplatform.ui.board

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.router.certificationplatform.GlobalApplication
import com.router.certificationplatform.model.Board
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class BoardWriteAcitivtyViewModel : ViewModel() {


    val database = Firebase.database
    val myRef = database.getReference("게시판")

    @RequiresApi(Build.VERSION_CODES.O)
    fun addBoard(certificate_name :String, title : String, contents : String) {

        //게시물 고유 번호 생성 (uid + 현재시간)
        val format = SimpleDateFormat("yyyyMddhhmmss")
        val currentDate = format.format(Date())
        val contents_id = GlobalApplication.user.uid + currentDate

        //게시물 추가
        var email = GlobalApplication.user.email.toString().substring(3)
        //var email = "***"+GlobalApplication.user.email.toString().substring(3)
        val board = Board(contents_id,title,contents,currentDate,email)
        myRef.child(certificate_name).child(contents_id).setValue(board)
    }


}