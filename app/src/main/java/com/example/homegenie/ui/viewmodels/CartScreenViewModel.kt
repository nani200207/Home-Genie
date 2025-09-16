package com.example.homegenie.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homegenie.models_.CartDetailsModel
import com.example.homegenie.repositories.GenieRepository
import com.example.homegenie.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val repository: GenieRepository
) : ViewModel() {

    var response by mutableStateOf(CartState())
        private set

    init {
        fetchCartItems()
    }

    private fun fetchCartItems() = viewModelScope.launch {
        repository.fetchCartItems().collect {
            when (it) {
                is ResultState.Success -> {
                    response = CartState(
                        data = it.data,
                        isLoading = false
                    )
                }

                is ResultState.Failure -> {
                    response = CartState(
                        error = it.msg.toString(),
                        isLoading = false
                    )
                }

                ResultState.Loading -> {
                    response = CartState(
                        isLoading = true
                    )
                }
            }
        }
    }

    fun removeCartItem(key: String) = repository.removeCartItem(key)


}

data class CartState(
    val data: List<CartDetailsModel> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)