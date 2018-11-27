package me.indexyz.strap.define.telegram

data class InputMediaAudio <T> (
    val type: String,
    val media: String,
    val thumb: T?,
    val caption: String?,
    val parse_mode: String?,
    val duration: Long?,
    val performer: String?,
    val title: String?
)