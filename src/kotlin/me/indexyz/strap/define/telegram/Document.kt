package me.indexyz.strap.define.telegram

data class Document (
    val file_id: String,
    val thumb: PhotoSize?,
    val file_name: String?,
    val mime_type: String?,
    val file_size: Long?
)