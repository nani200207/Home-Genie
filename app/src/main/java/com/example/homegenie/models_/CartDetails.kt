package com.example.homegenie.models_

data class CartDetailsModel(
    val item: CartItem?,
    val key: String? = ""
){

    data class CartItem(
        var uid : String? = null,
        var itemName : String? = null,
        var itemPrice : Double? = null,
        var itemQty : Long? = null,
    )

}
