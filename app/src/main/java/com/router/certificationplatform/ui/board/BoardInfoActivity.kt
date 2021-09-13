package com.router.certificationplatform.ui.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.router.certificationplatform.R
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
}