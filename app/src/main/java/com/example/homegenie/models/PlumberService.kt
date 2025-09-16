package com.example.homegenie.models

data class PlumberService(
    val serviceName: String,
    val servicePrice: Double
)

val plumberServices = listOf<PlumberService>(
    PlumberService(serviceName = "Pipe leakage", servicePrice = 4.99),
    PlumberService(serviceName = "Installations (taps, showers, basins, etc.)", servicePrice = 6.49),
    PlumberService(serviceName = "Water filters (installation & cleaning)", servicePrice = 9.99),
    PlumberService(serviceName = "Water blockage removal", servicePrice = 4.99),
    PlumberService(serviceName = "Toilet repairs", servicePrice = 8.49),
    PlumberService(serviceName = "Book a plumber", servicePrice = 4.99),
)