package com.example.homegenie.models

data class HousekeepingService(
    val serviceName: String,
    val servicePrice: Double
)

val housekeepingServices = listOf<HousekeepingService>(
    HousekeepingService(serviceName = "Complete home cleaning", servicePrice = 29.99),
    HousekeepingService(serviceName = "Windows cleaning", servicePrice = 9.99),
    HousekeepingService(serviceName = "Kitchen cleaning", servicePrice = 9.99),
    HousekeepingService(serviceName = "Bathroom cleaning", servicePrice = 12.99),
    HousekeepingService(serviceName = "Toilet cleaning", servicePrice = 12.99),
    HousekeepingService(serviceName = "Book a cleaner", servicePrice = 4.99),
)