package com.router.certificationplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity() {

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
    }
}