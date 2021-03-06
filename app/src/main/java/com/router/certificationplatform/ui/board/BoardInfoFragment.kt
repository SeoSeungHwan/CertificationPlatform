package com.router.certificationplatform.ui.board

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.router.certificationplatform.GlobalApplication
import com.router.certificationplatform.R
import kotlinx.android.synthetic.main.fragment_board_info.*
import kotlinx.android.synthetic.main.fragment_board_main.*
import java.text.SimpleDateFormat

class BoardInfoFragment : Fragment(R.layout.fragment_board_info) {

    private val viewModel by activityViewModels<BoardMainViewModel>()

    val args: BoardInfoFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //toolbar에 종목명 추가
        board_info_toolbar.inflateMenu(R.menu.board_info_menu)
        board_info_toolbar.title = viewModel.certificate_name

        //toolbar 뒤로가기 클릭 시
        board_info_toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //게시물 삭제 버튼 클릭 시
        board_info_toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id. board_info_remove_btn -> {
                    var builder = AlertDialog.Builder(context)
                    builder.setTitle("게시물 삭제")
                    builder.setMessage("게시물을 삭제하시겠습니까?")
                    builder.setIcon(R.drawable.ic_baseline_remove_circle_24)
                    //"예"라면 게시물 삭제하기
                    builder.setPositiveButton("예") { dialogInterface, i ->
                        viewModel.removeBoardInfo(args.boardId)
                        findNavController().popBackStack()
                        Toast.makeText(context, "게시물을 삭제하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                    builder.setNegativeButton("아니오") { dialogInterface, i ->

                    }
                    builder.show()
                    true
                }
                else -> false
            }
        }

        //글 정보 받아오기
        viewModel.fetchBoardInfo(args.boardId)
        viewModel.boardLiveData.observe(viewLifecycleOwner, {
            //만약 글 작성자와 자신이 일치하면 글 삭제 버튼 활성화
            board_info_toolbar.menu.findItem(R.id.board_info_remove_btn).isVisible = GlobalApplication.user.email.equals(it.writer)
            board_info_title.text = it.title
            board_info_writer.text = "***" + it.writer.substring(3)
            val nowformat = SimpleDateFormat("yyyyMMddhhmmss")
            val newformat = SimpleDateFormat("M/dd   hh:mm")
            val formatDate = nowformat.parse(it.time)
            val newformatDate = newformat.format(formatDate)
            board_info_time.text = newformatDate
            board_info_contents.text = it.contents
        })

        add_comment_btn.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("댓글 등록")
            builder.setMessage("댓글을 등록 하시겠습니까?")
            builder.setIcon(R.drawable.ic_baseline_add_comment_24)
            //"예"라면 게시물 삭제하기
            builder.setPositiveButton("예") { dialogInterface, i ->
                viewModel.addComment(args.boardId,comment_et.text.toString())
                comment_et.text = null

                val inputMethodManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
            builder.setNegativeButton("아니오") { dialogInterface, i ->

            }
            builder.show()
        }

        //댓글 목록 가져오기
        val linearLayoutMangerWrapper = LinearLayoutManager(context,
            RecyclerView.VERTICAL,
            false
        )

        viewModel.fetchComment(args.boardId)
        viewModel.commentListLivedata.observe(viewLifecycleOwner,{
            comment_count_tv.text = it.size.toString()
            val adapter = CommentRecyclerViewAdapter(it)
            comment_rv.layoutManager = linearLayoutMangerWrapper
            comment_rv.adapter = adapter
            //comment_rv.isNestedScrollingEnabled = false
            //todo 댓글이 많아지면 스크롤이 아니라 확장하게..


        })
    }
}