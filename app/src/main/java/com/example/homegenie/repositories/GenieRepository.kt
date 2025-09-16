package com.example.homegenie.repositories

import com.example.homegenie.models_.CartDetailsModel
import com.example.homegenie.models_.CustomerDetailsModel
import com.example.homegenie.models_.CustomerLocationDetailsModel
import com.example.homegenie.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface GenieRepository {

    fun insertItemToCart(item: CartDetailsModel.CartItem): Flow<ResultState<String>>

    fun removeCartItem(key: String): Flow<ResultState<String>>

    fun fetchCartItems() : Flow<ResultState<List<CartDetailsModel>>>

    //Customer

    fun insertC(
        item: CustomerDetailsModel.CustomerItem
    ): Flow<ResultState<String>>

    fun getItemC(): Flow<ResultState<CustomerDetailsModel>>

    fun checkItemC(): Flow<ResultState<Boolean>>


    fun deleteC(key: String): Flow<ResultState<String>>

    fun updateC(
        item: CustomerDetailsModel
    ): Flow<ResultState<String>>

    //Location
    fun insertL(
        item: CustomerLocationDetailsModel.CustomerLocationItem
    ): Flow<ResultState<String>>

    fun getLocation() : Flow<ResultState<CustomerLocationDetailsModel>>

    //fun deleteL(key: String) : Flow<ResultState<String>>

}