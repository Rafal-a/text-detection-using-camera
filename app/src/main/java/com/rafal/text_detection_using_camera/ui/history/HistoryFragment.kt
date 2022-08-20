package com.rafal.text_detection_using_camera.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.rafal.text_detection_using_camera.R
import com.rafal.text_detection_using_camera.base.BaseFragment
import com.rafal.text_detection_using_camera.databinding.FragmentHistoryBinding


class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {


    override val LOG_TAG: String = HistoryFragment::class.java.simpleName
    override val viewModel: HistoryViewModel by activityViewModels()
    override val bindingInflater: (LayoutInflater, Int, ViewGroup?, Boolean) -> FragmentHistoryBinding
     = DataBindingUtil::inflate

    override fun setupView() {
        binding.historyRecycler.adapter= HistoryAdapter(mutableListOf())
    }
}

