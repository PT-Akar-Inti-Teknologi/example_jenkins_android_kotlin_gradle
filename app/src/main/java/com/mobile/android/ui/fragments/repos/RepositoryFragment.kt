package com.mobile.android.ui.fragments.repos

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mobile.android.databinding.FragmentRepositoryBinding
import com.mobile.android.ui.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class RepositoryFragment: Fragment() {
    private val binding: FragmentRepositoryBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val viewModel: HomeViewModel by sharedViewModel()
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    private val adapter: ReposAdapter by lazy {
        ReposAdapter{item->
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
        binding.rvRepoList.adapter = adapter
        binding.rvRepoList.layoutManager = layoutManager
        viewModel.repos.observe(viewLifecycleOwner, {resources->
            resources.data?.let { list->
                binding.progressBarRepos.visibility = View.GONE
                if(list.isNotEmpty()){
                    binding.textReposNotFound.visibility = View.GONE
                    adapter.submitList(list)
                } else{
                    binding.textReposNotFound.visibility = View.VISIBLE
                    adapter.submitList(emptyList())
                }
            }
        })

        initSearchInputListener(view)
    }

    private fun initSearchInputListener(view: View) {
        binding.searchViewRepos.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView
        .OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                doSearch(view)
                binding.searchViewRepos.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        val clearButton = binding.searchViewRepos.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        clearButton.setOnClickListener {
            if(binding.searchViewRepos.query.isNotEmpty()){
                binding.searchViewRepos.setQuery("", false)
                dismissKeyboard(view.windowToken)
            }
        }
    }

    private fun doSearch(v: View) {
        val query = binding.searchViewRepos.query
            .toString().toLowerCase(Locale.getDefault()).trim()

        if(query.isBlank()) return

        dismissKeyboard(v.windowToken)
        viewModel.setKeywords(query)
        binding.progressBarRepos.visibility = View.VISIBLE
        binding.textReposNotFound.visibility = View.GONE
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}