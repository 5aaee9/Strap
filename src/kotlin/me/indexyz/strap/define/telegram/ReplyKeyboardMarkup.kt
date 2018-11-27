package me.indexyz.strap.define.telegram

data class ReplyKeyboardMarkup (
    val keyboard: List<List<KeyboardButton>>,
    val resize_keyboard: Boolean?,
    val one_time_keyboard: Boolean?,
    val selective: Boolean?
)