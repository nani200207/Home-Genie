package com.example.homegenie.models

data class SalonService(
    val serviceName: String,
    val servicePrice: Double
)

val salonServices = listOf<SalonService>(
    SalonService(serviceName = "Haircut (Men)", servicePrice = 2.49),
    SalonService(serviceName = "Haircut (Women)", servicePrice = 4.49),
    SalonService(serviceName = "Pedicure", servicePrice = 12.99),
    SalonService(serviceName = "Manicure", servicePrice = 14.99),
    SalonService(serviceName = "Facial", servicePrice = 8.49),
    SalonService(serviceName = "Waxing", servicePrice = 9.99),
    SalonService(serviceName = "Book a beautician", servicePrice = 4.99),
)