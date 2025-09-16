package com.example.homegenie.data

data class ShopItem(
    val productName: String,
    val productPrice: Double,
    val url: String
)

val shopItems = listOf<ShopItem>(
    ShopItem(productName = "Scissors", productPrice = 2.99, url = "https://images.unsplash.com/photo-1586363052121-5ef0f5b1fdb1?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2Npc3NvcnN8ZW58MHwxfDB8fHww"),
    ShopItem(productName = "Toilet Seat", productPrice = 35.99, url = "https://images.unsplash.com/photo-1563204719-44395a035bb6?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8dG9pbGV0JTIwc2VhdHxlbnwwfDF8MHx8fDA%3D"),
    ShopItem(productName = "Window Glass", productPrice = 19.49, url = "https://images.unsplash.com/photo-1527352774566-e4916e36c645?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8d2luZG93JTIwZ2xhc3N8ZW58MHwxfDB8fHww"),
    ShopItem(productName = "Table Chair Set", productPrice = 15.99, url = "https://images.unsplash.com/photo-1611269154421-4e27233ac5c7?q=80&w=1965&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    ShopItem(productName = "Drilling Machine", productPrice = 19.99, url = "https://images.unsplash.com/photo-1590635023142-73c3d34f2805?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8ZHJpbGxpbmclMjBtYWNoaW5lfGVufDB8MXwwfHx8MA%3D%3D"),
    ShopItem(productName = "Bulbs Set", productPrice = 25.39, url = "https://images.unsplash.com/photo-1630806293051-7b66c27ec042?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjN8fGxhbXB8ZW58MHwxfDB8fHww"),
    ShopItem(productName = "Hybrid Plants", productPrice = 49.49, url = "https://images.unsplash.com/photo-1601985705806-5b9a71f6004f?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fGh5YnJpZCUyMHBsYW50c3xlbnwwfDF8MHx8fDA%3D"),
    ShopItem(productName = "Wall Paint", productPrice = 9.99, url = "https://images.unsplash.com/photo-1585676737728-432f58d5fdba?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8cGFpbnQlMjBidWNrZXR8ZW58MHwxfDB8fHww"),
    ShopItem(productName = "Car Tyre", productPrice = 49.99, url = "https://images.unsplash.com/photo-1608479746923-7e17632a9799?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8dHlyZXxlbnwwfDF8MHx8fDA%3D"),
)
