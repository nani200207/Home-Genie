package com.example.homegenie.models

data class ElectricianService(
    val serviceName: String,
    val servicePrice: Double
)

val electricianServices = listOf<ElectricianService>(
    ElectricianService(serviceName = "AC repairs", servicePrice = 14.99),
    ElectricianService(serviceName = "Switch/Outlet repairs", servicePrice = 4.99),
    ElectricianService(serviceName = "Electronic panel upgrade", servicePrice = 9.99),
    ElectricianService(serviceName = "Electric appliance hookups", servicePrice = 6.99),
    ElectricianService(serviceName = "Wiring & rewiring", servicePrice = 19.99),
    ElectricianService(serviceName = "Book an electrician", servicePrice = 4.99),
)