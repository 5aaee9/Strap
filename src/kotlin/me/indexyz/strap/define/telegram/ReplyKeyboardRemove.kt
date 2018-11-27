package me.indexyz.strap.define.telegram

data class ReplyKeyboardRemove (
    val remove_keyboard: Boolean,
    val selective: Boolean?
)