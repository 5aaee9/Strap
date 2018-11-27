package me.indexyz.strap.define.telegram

data class InputMediaPhoto (
    val type: String,
    val media: String,
    val caption: String?,
    val parse_mode: String?
)