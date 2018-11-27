package me.indexyz.strap.define.telegram

data class StickerSet (
    val name: String,
    val title: String,
    val contains_masks: Boolean,
    val stickers: List<Sticker>
)