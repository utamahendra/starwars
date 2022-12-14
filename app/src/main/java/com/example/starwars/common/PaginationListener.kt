package com.example.starwars.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationListener(
    private val paginationLimit: Int,
    private val loadMoreAction: (itemCount: Int) -> Unit
) :
    RecyclerView.OnScrollListener() {
    var isLastPage: Boolean = false
    var itemCount: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if ((recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == itemCount && !isLastPage && dy > 0) {
            loadMoreAction.invoke(itemCount)
        }
    }
}