package com.github.dnaka91.beatfly.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.adapter.HistoryListAdapter
import com.github.dnaka91.beatfly.service.RadioService
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.history_list_fragment.*
import javax.inject.Inject

class HistoryListFragment : DaggerFragment() {

    @Inject
    internal lateinit var radioService: RadioService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.history_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = HistoryListAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = layoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))

        radioService.loadHistory().observe(this, Observer {
            adapter.setData(it.orEmpty().reversed())
        })
    }
}
