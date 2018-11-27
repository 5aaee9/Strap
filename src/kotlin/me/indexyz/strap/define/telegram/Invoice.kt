package me.indexyz.strap.define.telegram

data class Invoice (
    val title: String,
    val description: String,
    val start_parameter: String,
    val currency: String,
    val total_amount: Long
)