package com.example.homegenie.models

data class MechanicService(
    val serviceName: String,
    val servicePrice: Double
)

val mechanicServices = listOf<MechanicService>(
    MechanicService(serviceName = "Wheel alignment", servicePrice = 4.99),
    MechanicService(serviceName = "Vehicle maintenance", servicePrice = 14.99),
    MechanicService(serviceName = "Brake service repair", servicePrice = 6.99),
    MechanicService(serviceName = "Tyres (rotation, punctures, changing)", servicePrice = 9.99),
    MechanicService(serviceName = "Engine repairs", servicePrice = 29.99),
    MechanicService(serviceName = "Car AC repairs", servicePrice = 19.99),
    MechanicService(serviceName = "Book a mechanic", servicePrice = 9.99),
)