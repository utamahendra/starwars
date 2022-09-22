package com.example.starwars.presentation.people

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.starwars.R
import com.example.starwars.common.ErrorCode
import com.example.starwars.common.base.BaseActivity
import com.example.starwars.common.extension.gone
import com.example.starwars.common.extension.setSingleClickListener
import com.example.starwars.common.extension.visible
import com.example.starwars.common.viewbinding.viewBinding
import com.example.starwars.common.viewstate.ViewState
import com.example.starwars.databinding.ActivityPeopleListBinding
import com.example.starwars.domain.model.PeopleData
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleListActivity : BaseActivity() {

    private val binding by viewBinding(ActivityPeopleListBinding::inflate)

    private val viewModel by viewModel<PeopleListViewModel>()

    private val adapter by lazy { PeopleListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initListener()
        startObservingData()
        viewModel.getPeoplesBySearch("r2")
    }

    private fun initView() {
        binding.rvPeoples.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvPeoples.adapter = adapter
    }

    private fun initListener() {
        binding.srlPeoples.apply {
            setOnRefreshListener {
                isRefreshing = false
                adapter.clear()
                viewModel.getPeoples()
            }
        }
    }

    private fun startObservingData() {
        viewModel.peopleState.observe(this) { state ->
            when (state) {
                is ViewState.Loading -> {
                    hideError()
                    showLoading()
                }

                is ViewState.Error -> {
                    hideLoading()
                    showError(state.viewError?.errorCode.toString()) { viewModel.getPeoples() }
                }

                is ViewState.Success -> {
                    hideLoading()
                    showPeoples(state.data)
                }
            }
        }

        viewModel.peopleSearchState.observe(this) { state ->
            when (state) {
                is ViewState.Loading -> {
                    hideError()
                    showLoading()
                }

                is ViewState.Error -> {
                    hideLoading()
                    showError(state.viewError?.errorCode.toString()) { viewModel.getPeoples() }
                }

                is ViewState.Success -> {
                    hideLoading()
                    showPeoples(state.data)
                }
            }
        }
    }

    private fun showLoading() {
        binding.srlPeoples.gone()
        binding.sflPeoples.apply {
            visible()
            startShimmer()
        }
    }

    private fun hideLoading() {
        binding.sflPeoples.apply {
            gone()
            stopShimmer()
        }
        binding.srlPeoples.visible()
    }

    private fun showError(errorCode: String, action: () -> Unit) {
        binding.srlPeoples.gone()
        when (errorCode) {
            ErrorCode.GLOBAL_INTERNET_ERROR -> {
                binding.viewError.tvTitle.text = getString(R.string.connection_error)
            }

            else -> {
                binding.viewError.tvTitle.text = getString(R.string.internal_server_error)
            }
        }
        binding.viewError.btnRetry.visible()
        binding.viewError.btnRetry.setSingleClickListener { action.invoke() }
        binding.viewError.viewErrorContainer.visible()
    }

    private fun showEmpty() {
        binding.srlPeoples.gone()
        binding.viewError.tvTitle.text = getString(R.string.empty_data)
        binding.viewError.btnRetry.gone()
        binding.viewError.viewErrorContainer.visible()
    }

    private fun hideError() {
        binding.srlPeoples.visible()
        binding.viewError.viewErrorContainer.gone()
    }

    private fun showPeoples(data: List<PeopleData>) {
        hideError()
        adapter.resetData(data)
    }
}