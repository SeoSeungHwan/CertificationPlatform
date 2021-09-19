package com.router.certificationplatform.ui.board

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.router.certificationplatform.GlobalApplication
import com.router.certificationplatform.R
import com.router.certificationplatform.ui.sign.SignInActivity
import kotlinx.android.synthetic.main.activity_board_info.*
import kotlinx.android.synthetic.main.star_list_item.view.*

class BoardInfoActivity : AppCompatActivity() {

    private val viewModel: BoardInfoViewModel by viewModels()

    var board_id = ""
    var certificate_name = ""
    lateinit var menuitem : Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_info)

        board_id = intent.getStringExtra("board_id").toString()
        certificate_name = intent.getStringExtra("certificate_name").toString()
        board_info_toolbar.title = certificate_name
        setSupportActionBar(board_info_toolbar)

        //툴바 뒤로가기 버튼 클릭 시
        board_info_toolbar.setNavigationOnClickListener {
            val intent = Intent(this, BoardActivity::class.java)
            intent.putExtra("certificate_name", certificate_name)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchBoardInfo(certificate_name, board_id)
        viewModel.boardLiveData.observe(this, {

            //만약 글 작성자와 자신이 일치하면 글 삭제 버튼 활성화
            if(GlobalApplication.user.email.equals(it.writer)){
                menuitem.findItem(R.id.board_info_remove_btn).setVisible(true)
            }else{
                menuitem.findItem(R.id.board_info_remove_btn).setVisible(false)
            }

            board_info_title.text = it.title
            board_info_writer.text = "***" + it.writer.substring(3)
            board_info_contents.text = it.contents
        })
    }


    //툴바 세팅
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.board_info_menu, menu)
        if (menu != null) {
            menuitem = menu
        }
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //menu 선택시
        when (item.itemId) {
            //게시물 삭제 메뉴 눌렀을 때
            R.id.board_info_remove_btn -> {

                var builder = AlertDialog.Builder(this)
                builder.setTitle("게시물 삭제")
                builder.setMessage("게시물을 삭제하시겠습니까?")
                builder.setIcon(R.drawable.ic_baseline_remove_circle_24)
                //"예"라면 게시물 삭제하기
                builder.setPositiveButton("예") { dialogInterface, i ->

                    viewModel.removeBoardInfo(certificate_name,board_id)

                    val intent = Intent(this@BoardInfoActivity, BoardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("certificate_name",certificate_name)
                    startActivity(intent)

                    Toast.makeText(this, "게시물을 삭제하였습니다.", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("아니오") { dialogInterface, i ->

                }
                builder.show()


            }

        }
        return super.onOptionsItemSelected(item)
    }


}