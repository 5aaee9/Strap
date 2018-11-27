package me.indexyz.strap.define.telegram

data class Voice (
    val file_id: String,
    val width: Long,
    val height: Long,
    val duration: Long,
    val thumb: PhotoSize?,
    val file_name: String?,
    val mime_type: String?,
    val file_size: Long?
)