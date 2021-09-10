package com.router.certificationplatform.ui.board

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.router.certificationplatform.R
import com.router.certificationplatform.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity() {

    private val viewModel: BoardAcitivtyViewModel by viewModels()

    var certificate_name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
    }

    override fun onStart() {
        super.onStart()

        //종목명 초기화 및 툴바 초기화
        certificate_name = intent.getStringExtra("certificate_name").toString()
        board_toolbar.title =certificate_name
        setSupportActionBar(board_toolbar)


        //툴바 뒤로가기 버튼 클릭 시
        board_toolbar.setNavigationOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        //즐겨찾기 되어있는지 확인하기
        viewModel.checkStar(certificate_name)
        viewModel.starLiveData.observe(this@BoardActivity,{
            if(it){
                board_toolbar.menu.findItem(R.id.star_menu).setIcon(R.drawable.ic_baseline_star_24)
            }else{
                board_toolbar.menu.findItem(R.id.star_menu).setIcon(R.drawable.ic_baseline_star_border_24)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //menu 선택시
        when(item.itemId){
            //게시물 추가메뉴를 눌렀을 때
            R.id.add_menu -> {
                /*val intent = Intent(this,BoardWriteActivity::class.java)
                intent.putExtra("certificate_name",certificate_name)
                startActivity(intent)*/
            }
            //즐겨찾기 클릭시 삭제 및 추가
            R.id.star_menu -> {
                if(viewModel.starLiveData.value == true){
                    viewModel.removeStar(certificate_name)
                    viewModel.checkStar(certificate_name)
                }else{
                    viewModel.addStar(certificate_name)
                    viewModel.checkStar(certificate_name)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //툴바 세팅
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.board_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}