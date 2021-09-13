package com.router.certificationplatform.ui.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import com.router.certificationplatform.R
import com.router.certificationplatform.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_board_info.*

class BoardInfoActivity : AppCompatActivity() {

    private val viewModel: BoardInfoViewModel by viewModels()

    var board_id = ""
    var certificate_name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_info)

        board_id = intent.getStringExtra("board_id").toString()
        certificate_name = intent.getStringExtra("certificate_name").toString()
        board_info_toolbar.title =certificate_name
        setSupportActionBar(board_info_toolbar)

        //툴바 뒤로가기 버튼 클릭 시
        board_info_toolbar.setNavigationOnClickListener {
            val intent = Intent(this, BoardActivity::class.java)
            intent.putExtra("certificate_name",certificate_name)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchBoardInfo(certificate_name,board_id)
        viewModel.boardLiveData.observe(this,{
            board_info_title.text = it.title
            board_info_writer.text = "***"+it.writer.substring(3)
            board_info_contents.text = it.contents
        })
    }


    //툴바 세팅
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.board_info_menu,menu)
        return super.onCreateOptionsMenu(menu)

    }
}