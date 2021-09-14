package com.mobile.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.mobile.android.databinding.FragmentTabHomeBinding
import com.mobile.android.ui.fragments.contributor.ContributorFragment
import com.mobile.android.ui.fragments.repos.RepositoryFragment

class FragmentTabHome: Fragment() {
    private val binding: FragmentTabHomeBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val tabTitle = listOf<String>("Users", "Repositories")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerFragmentAdapter(requireActivity(), tabTitle.size)
        TabLayoutMediator(binding.tabLayoutMain, binding.viewPager){ tab, pos ->
            tab.text = tabTitle[pos]
        }.attach()
    }

    private class ViewPagerFragmentAdapter(
        activity: FragmentActivity,
        private val size: Int
    ): FragmentStateAdapter(activity){

        override fun getItemCount(): Int {
            return size
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                1->{
                    RepositoryFragment()
                }
                else->{
                    ContributorFragment()
                }
            }
        }
    }
}