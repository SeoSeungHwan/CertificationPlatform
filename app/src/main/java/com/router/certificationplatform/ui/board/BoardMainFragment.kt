package com.router.certificationplatform.ui.board

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.router.certificationplatform.R
import com.router.certificationplatform.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_board_main.*

class BoardMainFragment : Fragment() {

    private val viewModel by activityViewModels<BoardMainViewModel>()
    private lateinit var currentActivity : BoardMainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        //toolbar에 종목명 추가
        board_main_toolbar.inflateMenu(R.menu.board_menu)
        board_main_toolbar.title = viewModel.certificate_name

        //toolbar 뒤로가기 클릭 시
        board_main_toolbar.setNavigationOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        board_main_toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                //게시물 추가메뉴를 눌렀을 때
                R.id.add_menu -> {
                    //todo 게시물 작성 fragment 로 교체
                    findNavController().navigate(R.id.action_boardFragment_to_boardWriteFragment)
                    true
                }
                //즐겨찾기 클릭시 삭제 및 추가
                R.id.star_menu -> {
                    if(viewModel.starLiveData.value == true){
                        viewModel.removeStar()
                        viewModel.checkStar()
                    }else{
                        viewModel.addStar()
                        viewModel.checkStar()
                    }
                    true
                }
                else -> false
            }
        }

        val linearLayoutMangerWrapper = LinearLayoutManager(context,
            RecyclerView.VERTICAL,
            false
        )
        viewModel.fetchBoard()
        viewModel.boardListLivedata.observe(viewLifecycleOwner,{
            val adapter = BoardMainRecyclerViewAdapter(it)
            board_main_recyclerview.layoutManager = linearLayoutMangerWrapper
            board_main_recyclerview.adapter = adapter
            adapter.itemClick = object : BoardMainRecyclerViewAdapter.ItemClick{
                override fun onClick(view: View, position: Int,board_id:String) {
                //todo 게시글 상세 정보로 이동
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //activity 초기화
        currentActivity = activity as BoardMainActivity

    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_board_main, container, false)

        viewModel.checkStar()
        viewModel.starLiveData.observe(viewLifecycleOwner,{
            if(it){
                board_main_toolbar.menu.findItem(R.id.star_menu).setIcon(R.drawable.ic_baseline_star_24)
            }else{
                board_main_toolbar.menu.findItem(R.id.star_menu).setIcon(R.drawable.ic_baseline_star_border_24)
            }
        })

        return view
    }



}