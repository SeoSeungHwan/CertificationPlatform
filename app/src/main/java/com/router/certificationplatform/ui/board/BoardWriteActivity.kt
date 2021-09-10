package com.router.certificationplatform.ui.board

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.router.certificationplatform.R
import kotlinx.android.synthetic.main.activity_board_write.*

class BoardWriteActivity : AppCompatActivity() {

    private val viewModel: BoardWriteAcitivtyViewModel by viewModels()

    var certificate_name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)

    }

    override fun onStart() {
        super.onStart()

        //종목명 초기화 및 툴바이름 표시
        certificate_name = intent.getStringExtra("certificate_name").toString()
        board_write_toolbar.title ="글 쓰기"
        setSupportActionBar(board_write_toolbar)

        //툴바 뒤로가기 버튼 클릭 시
        board_write_toolbar.setNavigationOnClickListener {
            val intent = Intent(this,BoardActivity::class.java)
            intent.putExtra("certificate_name",certificate_name)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.board_write_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //menu 선택시
        when(item.itemId){
            //게시물 제출 메뉴를 눌렀을 때
            R.id.submit_menu -> {
                viewModel.addBoard(certificate_name,title_tv.text.toString(),contents_tv.text.toString())

                val intent = Intent(this,BoardActivity::class.java)
                intent.putExtra("certificate_name",certificate_name)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }


        return super.onOptionsItemSelected(item)
    }

}