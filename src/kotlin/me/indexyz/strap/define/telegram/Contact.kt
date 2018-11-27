package me.indexyz.strap.define.telegram

data class Contact (
    val phone_number: String,
    val first_name: String,
    val last_name: String?,
    val user_id: Long?,
    val vcard: String?
)