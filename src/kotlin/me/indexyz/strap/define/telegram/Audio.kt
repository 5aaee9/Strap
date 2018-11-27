package me.indexyz.strap.define.telegram

data class Audio (
    val file_id: String,
    val duration: Long,
    val performer: String?,
    val title: String?,
    val mime_type: String?,
    val file_size: Long?,
    val thumb: PhotoSize?
)