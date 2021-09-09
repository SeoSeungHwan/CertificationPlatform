package com.router.certificationplatform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.router.certificationplatform.ui.sign.SignInActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainAcitivtyViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Spinner에 자격증 종목 목록 나열
        viewModel.fetchCertificateList(0,1000,"TPjqY3dBCSVQ6T0f%2BBo7WsczzD%2FAy7pmHDdcXDJwRpeE8P4LVp%2Bxq8g8IaQcOLYGSkMWPi4ofPfwEuctz4DRGA%3D%3D")
        viewModel.certificateListLiveData.observe(this@MainActivity,{ certificateList ->
            certificateList.forEach {
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item, certificateList
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                certificate_list_spinner.adapter = adapter
            }
        })

    }

    override fun onStart() {
        super.onStart()
        certificate_list_spinner.setTitle("종목 목록")
        main_toolbar.title =GlobalApplication.user.displayName+"님 환영합니다."
        setSupportActionBar(main_toolbar)
    }

    //toolbar 세팅
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //로그아웃 버튼 클릭시
        when(item.itemId){
            R.id.logout_menu -> {
                var builder = AlertDialog.Builder(this)
                builder.setTitle("로그아웃")
                builder.setMessage("로그아웃 하시겠습니까?")
                builder.setIcon(R.drawable.ic_logout)
                //"예"라면 로그아웃하기
                builder.setPositiveButton("예") { dialogInterface, i ->
                    Firebase.auth.signOut()
                    //todo : GlobalApplication.user null로 바꾸기
                    val intent = Intent(this@MainActivity, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                builder.setNegativeButton("아니오") { dialogInterface, i ->

                }
                builder.show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    //뒤로가기 두번클릭시 나가게 하기
    var mBackWait: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - mBackWait >= 2000) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }
}