package com.example.homegenie.models

data class PainterService(
    val serviceName: String,
    val servicePrice: Double
)

val painterServices = listOf<PainterService>(
    PainterService(serviceName = "Complete interior painting", servicePrice = 149.99),
    PainterService(serviceName = "Complete exterior painting", servicePrice = 149.99),
    PainterService(serviceName = "Walls painting", servicePrice = 99.99),
    PainterService(serviceName = "Wood painting (doors, windows)", servicePrice = 49.99),
    PainterService(serviceName = "Roof painting", servicePrice = 49.99),
    PainterService(serviceName = "Book a painter", servicePrice = 9.99),

)