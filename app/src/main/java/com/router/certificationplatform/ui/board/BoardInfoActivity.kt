package com.router.certificationplatform.ui.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.router.certificationplatform.R

class BoardInfoActivity : AppCompatActivity() {

    var board_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_info)
        board_id = intent.getStringExtra("board_id").toString()
        Toast.makeText(this,board_id,Toast.LENGTH_LONG).show()

    }

    override fun onStart() {
        super.onStart()


    }
}