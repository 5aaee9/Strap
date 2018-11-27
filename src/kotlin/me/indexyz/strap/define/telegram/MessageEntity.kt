package me.indexyz.strap.define.telegram

data class MessageEntity (
    val type: String,
    val offset: Long,
    val length: Long,
    val url: String?,
    val user: User?
)