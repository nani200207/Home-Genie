package com.example.homegenie.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homegenie.models_.CustomerDetailsModel
import com.example.homegenie.repositories.GenieRepository
import com.example.homegenie.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(private val repository: GenieRepository) : ViewModel() {

    var registrationUiState by mutableStateOf(RegistrationUiState())
        private set

    var response by mutableStateOf(CustState())
        private set

    init {
        getDetails()
    }

    fun update(item: CustomerDetailsModel) = repository.updateC(item)

    fun getCustomer() = repository.getItemC()

    fun getDetails() = viewModelScope.launch {
        repository.getItemC().collect{
            when(it){
                is ResultState.Success->{
                    response = CustState(
                        data = it.data
                    )
                }
                is ResultState.Failure->{
                    response = CustState(
                        error = it.msg.toString()
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

data class CustState(
    val data: CustomerDetailsModel = CustomerDetailsModel(CustomerDetailsModel.CustomerItem()),
    val error: String = "",
    val isLoading: Boolean = false
)
