package com.router.certificationplatform.ui.board

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.router.certificationplatform.GlobalApplication
import com.router.certificationplatform.model.Board
import com.router.certificationplatform.model.Comment
import java.sql.Timestamp
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
    //즐겨찾기 추가
    fun addStar(){
        starRef.child(GlobalApplication.user.uid).child(certificate_name).setValue("true")
    }
    //즐겨찾기 삭제
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
        val board_id = boardRef.push().key.toString()
        //게시물 추가
        var email = GlobalApplication.user.email.toString()
        //var email = "***"+GlobalApplication.user.email.toString().substring(3)
        val board = Board(board_id,title,contents,currentDate,email)
        boardRef.child(certificate_name).child(board_id).setValue(board)
    }

    //게시판 정보 가져오기
    val boardLiveData = MutableLiveData<Board>()
    fun fetchBoardInfo(board_id : String) {

        boardRef.child(certificate_name).child(board_id).get().addOnSuccessListener {
            boardLiveData.value = it.getValue(Board::class.java)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    //게시판 삭제
    fun removeBoardInfo(board_id: String){
        boardRef.child(certificate_name).child(board_id).removeValue()
    }

    //댓글 추가
    fun addComment(board_id: String, contents: String){

        val commentId = boardRef.push().key.toString()

        //게시물 고유 번호 생성 (uid + 현재시간)
        val format = SimpleDateFormat("yyyyMddhhmmss")
        val currentDate = format.format(Date())
        val comment = Comment(commentId,GlobalApplication.user.uid,GlobalApplication.user.email.toString(),contents,currentDate)

        boardRef.child(certificate_name).child(board_id).child("Comments").child(boardRef.push().key.toString()).setValue(comment)
        fetchComment(board_id)
    }
    
    //댓글 불러오기
    val commentList = ArrayList<Comment>()
    val commentListLivedata = MutableLiveData<ArrayList<Comment>>()
    fun fetchComment(board_id: String){
        commentList.clear()
        boardRef.child(certificate_name).child(board_id).child("Comments").get().addOnSuccessListener {
            it.children.forEach {
                val comment = it.getValue(Comment::class.java)
                if (comment != null) {
                    commentList.add(comment)
                }
            }
            commentListLivedata.value = commentList
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}