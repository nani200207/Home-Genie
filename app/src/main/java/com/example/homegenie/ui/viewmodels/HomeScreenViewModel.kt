package com.example.homegenie.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homegenie.models_.CustomerLocationDetailsModel
import com.example.homegenie.repositories.GenieRepository
import com.example.homegenie.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: GenieRepository
) : ViewModel() {

    var location by mutableStateOf(LocState())
        private set

    var response by mutableStateOf(CustState())
        private set

    init {
        getLocation()
        getDetails()
    }

    private fun getLocation() = viewModelScope.launch {
        repository.getLocation().collect{
            when(it){
                is ResultState.Success->{
                    location = LocState(
                        data = it.data,
                        isLoading = false
                    )
                }
                is ResultState.Failure->{
                    location = LocState(
                        error = it.msg.toString(),
                        isLoading = false
                    )
                }
                ResultState.Loading->{
                    location = LocState(
                        isLoading = true
                    )
                }
            }
        }
    }

    fun getDetails() = viewModelScope.launch {
        repository.getItemC().collect{
            when(it){
                is ResultState.Success->{
                    response = CustState(
                        data = it.data,
                        isLoading = false
                    )
                }
                is ResultState.Failure->{
                    response = CustState(
                        error = it.msg.toString(),
                        isLoading = false
                    )
                }
                ResultState.Loading->{
                    response = CustState(
                        isLoading = true
                    )
                }
            }
        }
    }

}

data class LocState(
    val data: CustomerLocationDetailsModel = CustomerLocationDetailsModel(
        CustomerLocationDetailsModel.CustomerLocationItem()),
    val error: String = "",
    val isLoading: Boolean = false
)