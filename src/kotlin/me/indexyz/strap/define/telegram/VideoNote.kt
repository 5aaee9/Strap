package me.indexyz.strap.define.telegram

data class VideoNote (
    val file_id: String,
    val length: Long,
    val duration: Long,
    val thumb: PhotoSize?,
    val file_size: Long?
)