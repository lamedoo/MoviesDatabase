package com.lukakrodzaia.moviesdatabase.ui.baseclasses

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.lukakrodzaia.moviesdatabase.helpers.OpenFragmentListener

abstract class BaseFragment<VB: ViewBinding>: Fragment() {
    protected var openFragmentListener: OpenFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        openFragmentListener = context as? OpenFragmentListener
    }

    private var _binding: VB? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return _binding!!.root
    }

    override fun onDetach() {
        super.onDetach()
        openFragmentListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}