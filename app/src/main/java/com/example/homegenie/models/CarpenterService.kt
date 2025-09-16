package com.example.homegenie.models

data class CarpenterService(
    val serviceName: String,
    val servicePrice: Double
)

val carpenterServices = listOf<CarpenterService>(
    CarpenterService(serviceName = "Furniture repairs & installations", servicePrice = 9.99),
    CarpenterService(serviceName = "Door and window repairs", servicePrice = 9.99),
    CarpenterService(serviceName = "Wall drilling", servicePrice = 4.99),
    CarpenterService(serviceName = "TV installation", servicePrice = 4.99),
    CarpenterService(serviceName = "Furniture polishing", servicePrice = 14.99),
    CarpenterService(serviceName = "Book a carpenter", servicePrice = 4.99),
)