package me.indexyz.strap.define.telegram

data class UserProfilePhotos (
    val total_count: Long,
    val photos: List<List<PhotoSize>>
)