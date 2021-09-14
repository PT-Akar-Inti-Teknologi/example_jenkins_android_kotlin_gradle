package com.mobile.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mobile.android.databinding.FragmentContributorDetailBinding

/**
 *  Nah fam, the effort is to high for now
 *  Just redirect to web version
 */
class FragmentContributorDetail: Fragment() {
    private val binding: FragmentContributorDetailBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


}