package com.example.homegenie.models_

data class CustomerDetailsModel(
    val item: CustomerItem?,
    val key: String? = ""
){

    data class CustomerItem(
        var cid: String? = "",
        var name: String? = "",
        var gender: String? = "",
        var age: Long? = null,
        var mobileNumber: Long? = null,
    )

}
