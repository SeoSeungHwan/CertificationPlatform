package com.router.certificationplatform.ui.board

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.router.certificationplatform.GlobalApplication
import com.router.certificationplatform.model.Board
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BoardMainViewModel : ViewModel(){
    var certificate_name = ""
    val starLiveData = MutableLiveData<Boolean>()
    val database = Firebase.database
    val starRef = database.getReference("즐겨찾기")
    val boardRef = database.getReference("게시판")


    //즐겨찾기가 되어있는지 확인 후 별 체크
    fun checkStar(){
        starRef.child(GlobalApplication.user.uid).child(certificate_name).get().addOnSuccessListener {
            starLiveData.value = it.value != null
        }.addOnFailureListener {
        }
    }
    fun addStar(){
        starRef.child(GlobalApplication.user.uid).child(certificate_name).setValue("true")
    }
    fun removeStar(){
        starRef.child(GlobalApplication.user.uid).child(certificate_name).removeValue()
    }

    //게시글 목록 가져오기
    val boardList = ArrayList<Board>()
    val boardListLivedata = MutableLiveData<ArrayList<Board>>()
    fun fetchBoard() {
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

    //게시물 추가
    fun addBoard( title : String, contents : String) {

        //게시물 고유 번호 생성 (uid + 현재시간)
        val format = SimpleDateFormat("yyyyMddhhmmss")
        val currentDate = format.format(Date())
        val contents_id = GlobalApplication.user.uid + currentDate

        //게시물 추가
        var email = GlobalApplication.user.email.toString()
        //var email = "***"+GlobalApplication.user.email.toString().substring(3)
        val board = Board(contents_id,title,contents,currentDate,email)
        boardRef.child(certificate_name).child(contents_id).setValue(board)
    }

}