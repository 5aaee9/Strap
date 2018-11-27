package me.indexyz.strap.define.telegram

data class KeyboardButton (
    val text: String,
    val request_contact: Boolean?,
    val request_location: Boolean?
)