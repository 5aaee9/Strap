package me.indexyz.strap.define.telegram

data class PhotoSize (
    val file_id: String,
    val width: Long,
    val height: Long,
    val file_size: Long?
)