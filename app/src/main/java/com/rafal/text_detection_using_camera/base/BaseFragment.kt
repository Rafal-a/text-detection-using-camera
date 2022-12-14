package com.rafal.text_detection_using_camera.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rafal.text_detection_using_camera.BR


abstract class BaseFragment<VDB: ViewDataBinding>(private val fragmentLayoutId: Int): Fragment() {

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory = SavedStateViewModelFactory(requireActivity().application, this, arguments ?: Bundle())

    protected abstract val LOG_TAG: String
    abstract val viewModel: ViewModel

    private lateinit var _binding: VDB
    val binding: VDB get() = _binding
    abstract val bindingInflater: (LayoutInflater, Int, ViewGroup?, Boolean) -> VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, fragmentLayoutId, container, false)
        _binding.setVariable(BR.viewModel, viewModel)
        _binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    abstract fun setupView()

}