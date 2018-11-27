package me.indexyz.strap.define.telegram

data class ShippingOption (
    val id: String,
    val title: String,
    val prices: List<LabeledPrice>
)