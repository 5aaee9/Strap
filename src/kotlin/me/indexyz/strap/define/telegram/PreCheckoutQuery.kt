package me.indexyz.strap.define.telegram

data class PreCheckoutQuery (
    val id: String,
    val from: User,
    val currency: String,
    val total_amount: Long,
    val invoice_payload: String,
    val shipping_option_id: String?,
    val order_info: OrderInfo?
)