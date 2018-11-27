package me.indexyz.strap.define.telegram

data class User (
    val id: Long,
    val is_bot: Boolean,
    val first_name: String,
    val last_name: String,
    val username: String?,
    val language_code: String?
)