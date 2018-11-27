package me.indexyz.strap.define.telegram

data class InputMediaAnimation <T> (
    val type: String,
    val media: String,
    val thumb: T?,
    val caption: String?,
    val parse_mode: String?,
    val width: Long?,
    val height: Long?,
    val duration: Long?
)