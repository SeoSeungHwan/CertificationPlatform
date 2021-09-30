package com.router.certificationplatform.ui.board

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.router.certificationplatform.R
import com.router.certificationplatform.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_board_write.*
import kotlinx.android.synthetic.main.activity_board_write.board_write_toolbar
import kotlinx.android.synthetic.main.fragment_board_main.*
import kotlinx.android.synthetic.main.fragment_board_write.*


class BoardWriteFragment : Fragment() {

    private val viewModel by activityViewModels<BoardMainViewModel>()

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //toolbar에 종목명 추가
        board_write_toolbar.inflateMenu(R.menu.board_write_menu)
        board_write_toolbar.title = "글쓰기"

        //toolbar 뒤로가기 클릭 시
        board_write_toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        board_write_toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                //게시물 추가메뉴를 눌렀을 때
                R.id.submit_menu -> {
                    viewModel.addBoard(board_write_title_tv.text.toString(),board_write_contents_tv.text.toString())
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_board_write, container, false)
    }
}