package com.example.starwars.presentation.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.common.viewstate.ViewState
import com.example.starwars.domain.model.PeopleData
import com.example.starwars.domain.usecase.PeopleSearchUseCase
import com.example.starwars.domain.usecase.PeopleUseCase
import kotlinx.coroutines.launch

class PeopleListViewModel(
    private val peopleUseCase: PeopleUseCase,
    private val peopleSearchUseCase: PeopleSearchUseCase
) : ViewModel() {

    val peopleState = MutableLiveData<ViewState<List<PeopleData>>>()

    val peopleSearchState = MutableLiveData<ViewState<List<PeopleData>>>()

    fun getPeoples() {
        viewModelScope.launch {
            peopleState.postValue(ViewState.Loading())
            peopleUseCase.invoke(Unit).handleResult({ userDetailData ->
                peopleState.postValue(ViewState.Success(userDetailData))
            }, { viewError ->
                peopleState.postValue(ViewState.Error(viewError))
            })
        }
    }

    fun getPeoplesBySearch(keyword: String) {
        viewModelScope.launch {
            peopleState.postValue(ViewState.Loading())
            peopleSearchUseCase.invoke(keyword).handleResult({ userDetailData ->
                peopleState.postValue(ViewState.Success(userDetailData))
            }, { viewError ->
                peopleState.postValue(ViewState.Error(viewError))
            })
        }
    }
}