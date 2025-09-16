package com.example.homegenie.models_

data class CustomerLocationDetailsModel(
    val item: CustomerLocationItem?,
    val key: String? = ""
) {
    data class CustomerLocationItem(
        var uid: String? = "",
        var lat: Double? = null,
        var lng: Double? = null,
        var geoHash: String? = "",
        var houseNumber: String? = "",
        var locality: String? = ""
    )
}