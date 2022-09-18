package com.example.starwars.presentation.people

import android.view.View
import android.view.ViewGroup
import com.example.starwars.R
import com.example.starwars.common.base.BaseAdapter
import com.example.starwars.common.base.BaseHolder
import com.example.starwars.common.extension.inflate
import com.example.starwars.domain.model.PeopleData
import kotlinx.android.synthetic.main.item_people.view.*

class PeopleListAdapter : BaseAdapter<PeopleData, PeopleListAdapter.PeopleHolder>() {

    override fun bindViewHolder(holder: PeopleHolder, data: PeopleData?) {
        data?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder = PeopleHolder(
        parent.inflate(
            R.layout.item_people, false
        )
    )

    inner class PeopleHolder(view: View) : BaseHolder<PeopleData>(listener, view) {
        fun bind(data: PeopleData) {
            with(itemView) {
                tv_name.text = data.name
                tv_height.text = "${data.height} cm"
                tv_mass.text = "${data.mass} kg"
            }
        }
    }
}