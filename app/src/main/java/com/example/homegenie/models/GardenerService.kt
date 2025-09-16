package com.example.homegenie.models

data class GardenerService(
    val serviceName: String,
    val servicePrice: Double
)

val gardenerServices = listOf<GardenerService>(
    GardenerService(serviceName = "Tree pruning", servicePrice = 9.99),
    GardenerService(serviceName = "Shrub & hedge trimming", servicePrice = 14.99),
    GardenerService(serviceName = "Lawn moving & maintenance", servicePrice = 9.99),
    GardenerService(serviceName = "New construction", servicePrice = 29.99),
    GardenerService(serviceName = "Book a gardener", servicePrice = 4.99),
)