package com.mobile.android.ui.fragments.contributor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mobile.android.databinding.FragmentContributorBinding
import com.mobile.android.ui.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ContributorFragment: Fragment() {
    private val binding: FragmentContributorBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val viewModel: HomeViewModel by sharedViewModel()
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    private val adapter: ContributorAdapter by lazy {
        ContributorAdapter{item->
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(item.htmlUrl)
            }
            requireActivity().startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvContributorList.adapter = adapter
        binding.rvContributorList.layoutManager = layoutManager
        viewModel.contributor.observe(viewLifecycleOwner, {resources->
            resources.data?.let { list->
                binding.progressBarContributor.visibility = View.GONE
                if(list.isNotEmpty()){
                    binding.textContributorOffline.visibility = View.GONE
                    adapter.submitList(resources.data)
                }else{
                    binding.textContributorOffline.visibility = View.VISIBLE
                    adapter.submitList(emptyList())
                }
            }
        })
    }
}