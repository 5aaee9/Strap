package me.indexyz.strap.define.telegram

data class Sticker (
    val file_id: String,
    val width: Long,
    val height: Long,
    val thumb: PhotoSize?,
    val emoji: String?,
    val set_name: String?,
    val mask_position: MaskPosition?,
    val file_size: Long?
)