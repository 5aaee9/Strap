package me.indexyz.strap.define.telegram

data class InputMediaDocument <T> (
    val type: String,
    val media: String,
    val thumb: T?,
    val caption: String?,
    val parse_mode: String?
)