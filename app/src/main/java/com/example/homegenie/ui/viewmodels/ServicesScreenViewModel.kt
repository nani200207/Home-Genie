package com.example.homegenie.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.homegenie.models_.CartDetailsModel
import com.example.homegenie.repositories.GenieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServicesScreenViewModel @Inject constructor(
    private val repository: GenieRepository
) : ViewModel() {

    fun insertItemToCart(item: CartDetailsModel.CartItem) = repository.insertItemToCart(item)

    fun removeCartItem(key: String) = repository.removeCartItem(key)

}