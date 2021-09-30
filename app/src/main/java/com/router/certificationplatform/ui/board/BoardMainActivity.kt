package com.router.certificationplatform.ui.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.router.certificationplatform.R

class BoardMainActivity : AppCompatActivity() {

    private val viewModel: BoardMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_main)

        //종목명
        viewModel.certificate_name = intent.getStringExtra("certificate_name").toString()

    }


}